package com.ssafy.doit.controller;

import com.ssafy.doit.domain.UserRole;
import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.request.RequestLoginUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping("/join")
    public Object join(@RequestBody User user) {
        BasicResponse result = new BasicResponse();

        try {
            userRepository.save(User.builder()
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .nickname(user.getNickname())
                    .userRole(UserRole.USER) // 최초 가입시 USER 로 설정
                    .build()).getId();

            result.status = true;
            result.data = "success";
        }
        catch (Exception e){
            result.status = false;
            result.data = "중복된 이메일 입니다.";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public Object login(@RequestBody RequestLoginUser user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());

        BasicResponse result = new BasicResponse();
        result.status = false;

        if(!userOpt.isPresent()){
            result.data = "해당 이메일이 존재하지 않습니다.";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        User member = userOpt.get();
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword()))
            result.data = "잘못된 비밀번호입니다.";
        else{
            result.status = true;
            result.object = jwtUtil.generateToken(member);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
