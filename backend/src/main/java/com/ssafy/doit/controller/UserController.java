package com.ssafy.doit.controller;

import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.request.RequestLoginUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.EmailSendService;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSendService emailSendService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public Object info(HttpServletRequest req){
        BasicResponse result = new BasicResponse();

        try {
            Map<String, Object> userMap = (Map<String, Object>) jwtUtil.getUser(req.getHeader("accessToken"));
            System.out.println(userMap);
            User user = userRepository.findByEmail((String) userMap.get("email")).get();

            result.status = true;
            result.data = "success";
            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 닉네임 중복 확인
    @PostMapping("/checkNick")
    public Object checkNickname(@RequestBody String nickname){
        BasicResponse result = new BasicResponse();
        if(userRepository.findByNickname(nickname).isPresent()){
            result.status = true;
            result.data = "success";
        }else{
            System.out.println("닉네임 중복");
            result.status = false;
            result.data = "중복된 닉네임 입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 이메일 중복 확인
    @PostMapping("/checkEmail")
    public Object checkEmail(@RequestBody String email) {
        BasicResponse result = new BasicResponse();
        if (userRepository.findByEmail(email).isPresent()) {
            result.status = true;
            result.data = "success";
        } else {
            System.out.println("이메일 중복");
            result.status = false;
            result.data = "중복된 이메일 입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원가입
    @PostMapping("/signup")
    public Object signup(@RequestBody User request) {
        BasicResponse result = new BasicResponse();
        try {
            String authKey = emailSendService.sendAuthMail(request.getEmail());
            userRepository.save(User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .userRole(UserRole.GUEST) // 최초 가입시 인증 받기 전에는 GUEST
                    .authKey(authKey)
                    .build()).getId();

            result.status = true;
            result.data = "success";
        }
        catch (Exception e){
            result.status = false;
            result.data = "실패";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 이메일 인증 재전송
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody User request){
        String authKey = emailSendService.sendAuthMail(request.getEmail());
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        user.ifPresent(selectUser ->{
            selectUser.setAuthKey(authKey);
            userRepository.save(selectUser);
        });
    }
    // 이메일 인증 확인
    @GetMapping("/authEmail")
    public void confirmEmail(@RequestParam String email, @RequestParam String authKey){
        Optional<User> user = userRepository.findByEmailAndAuthKey(email, authKey);

        user.ifPresent(selectUser ->{
            selectUser.setUser_role(UserRole.USER);
            userRepository.save(selectUser);
        });
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
