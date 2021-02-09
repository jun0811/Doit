package com.ssafy.doit.controller;

import com.ssafy.doit.model.Mileage;
import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.repository.MileageRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.EmailSendService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class SignupController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private MileageRepository mileageRepository;
    private final PasswordEncoder passwordEncoder;

    // 닉네임 중복 확인
    @ApiOperation(value = "닉네임 중복 확인")
    @PostMapping("/checkNick")
    public Object checkNickname(@RequestBody String nickname){
        ResponseBasic result = new ResponseBasic();
        System.out.println(UserRole.USER);
        Optional<User> optUser = userRepository.findByNicknameAndUserRole(nickname, UserRole.USER);
        Optional<User> optGuest = userRepository.findByNicknameAndUserRole(nickname, UserRole.GUEST);
        if(optUser.isPresent() || optGuest.isPresent()){
            result = new ResponseBasic(false, "중복된 닉네임입니다.", null);
        }else{
            result = new ResponseBasic( true, "success", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 이메일 중복 확인
    @ApiOperation(value = "이메일 중복 확인")
    @PostMapping("/checkEmail")
    public Object checkEmail(@RequestBody String email) {
        ResponseBasic result = new ResponseBasic();
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("이메일 중복");
            result.status = false;
            result.data = "중복된 이메일 입니다.";
        } else {
            result.status = true;
            result.data = "success";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원가입
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public Object signup(@RequestBody User request) {
        ResponseBasic result = new ResponseBasic();
        try {
            String authKey = emailSendService.sendSignupMail(request.getEmail());
            User user = userRepository.save(User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .userRole(UserRole.GUEST)
                    .authKey(authKey)
                    .build());

            mileageRepository.save(Mileage.builder()
                    .content("회원가입 축하 마일리지 지급")
                    .date(LocalDate.now())
                    .user(user).build());

            result.status = true;
            result.data = "success";
        }
        catch (Exception e){
            result.status = false;
            result.data = "실패";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원가입 이메일 인증 전송
    @ApiOperation(value = "회원가입 이메일 인증 전송")
    @PostMapping("/sendSignupEmail")
    public void sendSignupEmail(@RequestBody User request){
        String authKey = emailSendService.sendSignupMail(request.getEmail());
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        user.ifPresent(selectUser ->{
            selectUser.setAuthKey(authKey);
            userRepository.save(selectUser);
        });
    }

    // 회원가입 이메일 인증 확인
    @ApiOperation(value = "회원가입 이메일 인증 확인")
    @GetMapping("/confirmSignup")
    public Object confirmSignup(@RequestParam String email, @RequestParam String authKey){
        Optional<User> user = userRepository.findByEmailAndAuthKey(email, authKey);

        user.ifPresent(selectUser ->{
            selectUser.setUserRole(UserRole.USER);
            userRepository.save(selectUser);
        });

//        mileageRepository.save(Mileage.builder()
//                .content("회원가입 축하 마일리지 지급")
//                .date(LocalDate.now())
//                .user(user.get()).build());

        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 비밀번호 변경 이메일 인증 전송
    @ApiOperation(value = "비밀번호 변경 이메일 인증 전송")
    @PostMapping("/sendChangePwEmail")
    public Object sendChangePwEmail(@RequestBody User request){
        ResponseBasic result = new ResponseBasic();

        String authKey = emailSendService.sendChangePwMail(request.getEmail());
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(!user.isPresent()) {
            result.status = false;
            result.data = "해당 이메일이 존재하지 않습니다.";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        user.ifPresent(selectUser -> {
            selectUser.setAuthKey(authKey);
            userRepository.save(selectUser);
        });
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 비밀번호 변경 이메일 인증 확인
    @ApiOperation(value = "비밀번호 변경 이메일 인증 확인")
    @PostMapping("/confirmPw")
    public Object confirmPw(@RequestBody RequestChangePw request){
        Optional<User> user = userRepository.findByEmailAndAuthKey(request.getEmail(), request.getAuthKey());

        user.ifPresent(selectUser ->{
            selectUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(selectUser);
        });
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
