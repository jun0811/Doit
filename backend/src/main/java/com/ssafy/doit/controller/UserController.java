package com.ssafy.doit.controller;

import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.request.RequestLoginUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.EmailSendService;
import com.ssafy.doit.service.ImageService;
import com.ssafy.doit.service.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    private ImageService imageService;

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
    @ApiOperation(value = "닉네임 중복 확인")
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
    @ApiOperation(value = "이메일 중복 확인")
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
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public Object signup(@RequestBody User request) {
        BasicResponse result = new BasicResponse();
        try {
            String authKey = emailSendService.sendSignupMail(request.getEmail());
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
    @GetMapping("/signupEmail")
    public Object signupEmail(@RequestParam String email, @RequestParam String authKey){
        Optional<User> user = userRepository.findByEmailAndAuthKey(email, authKey);

        user.ifPresent(selectUser ->{
            selectUser.setUser_role(UserRole.USER);
            userRepository.save(selectUser);
        });
        BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 비밀번호 변경 이메일 인증 전송
    @ApiOperation(value = "비밀번호 변경 이메일 인증 전송")
    @PostMapping("/sendChangePwEmail")
    public Object sendChangePwEmail(@RequestBody User request){
        BasicResponse result = new BasicResponse();

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
    @PostMapping("/changePwEmail")
    public Object ChangePwEmail(@RequestBody RequestChangePw request){
        Optional<User> user = userRepository.findByEmailAndAuthKey(request.getEmail(), request.getAuthKey());

        user.ifPresent(selectUser ->{
            selectUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(selectUser);
        });
        BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 로그인
    @ApiOperation(value = "로그인")
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

    // 회원정보 수정
    @ApiOperation(value = "회원정보 수정")
    @PutMapping("/updateInfo/{email}")
    public Object updateInfo(@RequestBody(required = true) Map<String, String> map) {
        BasicResponse result = new BasicResponse();
        String email = map.get("email");
        String name = map.get("name");
//        System.out.println(name);
        Optional<User> userInfo = userRepository.findUserByEmail(email);

        if (userInfo.isPresent() ) {
            userInfo.ifPresent(selectUser->{
                selectUser.setNickname(name);
                userRepository.save(selectUser);
            });
            result.status = true;
            result.data = "success";
        }
        else{
            result.status = false;
            result.data = "실패";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "프로필사진 변경")
    @PutMapping("/insertImg")
    public Object insertImg(HttpServletRequest req, @RequestParam("image") MultipartFile mFile, Model model) {
        BasicResponse result = new BasicResponse();
        String upload_path = "C:/Users/multicampus/IdeaProjects/s04p12c108/backend/src/main/resources/static/images/profile/"; // 프로필 사진들 모아두는 폴더

        try {
            Map<String, Object> userMap = (Map<String, Object>) jwtUtil.getUser(req.getHeader("accessToken"));
            System.out.println(userMap);
            User user = userRepository.findByEmail((String) userMap.get("email")).get();

            Optional<User> userInfo = userRepository.findUserByEmail(user.getEmail());
            System.out.println(userInfo);

            String redirect_url = "redirect:/main/user/insertImg/" + user.getEmail(); // 사진업로드 이후 redirect될 url

            if (user.getImage() != null) { // 이미 프로필 사진이 있을경우
                File file = new File(upload_path + user.getImage()); // 경로 + 유저 프로필사진 이름을 가져와서
                file.delete(); // 원래파일 삭제
            }
            mFile.transferTo(new File(upload_path + mFile.getOriginalFilename()));  // 경로에 업로드

            imageService.imgUpdate(user.getEmail(),mFile.getOriginalFilename());
            result.status = true;
            result.data = "profile upload success";
            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 탈퇴 ... 미완성
    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/deleteUser")
    public Object deleteUser(HttpServletRequest req) {
        BasicResponse result = new BasicResponse();

        try {
            Map<String, Object> userMap = (Map<String, Object>) jwtUtil.getUser(req.getHeader("accessToken"));
            System.out.println(userMap);
            User user = userRepository.findByEmail((String) userMap.get("email")).get();

            Optional<User> userInfo = userRepository.findUserByEmail(user.getEmail());
            System.out.println(userInfo);

            userInfo.ifPresent(selectUser->{
                selectUser.setUser_role(UserRole.GUEST);
                userRepository.save(selectUser);
            });
            
            result.status = true;
            result.data = "탈퇴 success";
            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
