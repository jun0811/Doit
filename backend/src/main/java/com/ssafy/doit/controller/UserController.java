package com.ssafy.doit.controller;

import com.ssafy.doit.model.Profile;
import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.request.RequestLoginUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.ProfileRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.EmailSendService;
import com.ssafy.doit.service.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public Object info(HttpServletRequest req){
        ResponseBasic result = new ResponseBasic();

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
        ResponseBasic result = new ResponseBasic();
        if(userRepository.findByNickname(nickname).isPresent()){
            System.out.println("닉네임 중복");
            result.status = false;
            result.data = "중복된 닉네임 입니다.";
        }else{
            result.status = true;
            result.data = "success";
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
    @GetMapping("/confirmSignup")
    public Object confirmSignup(@RequestParam String email, @RequestParam String authKey){
        Optional<User> user = userRepository.findByEmailAndAuthKey(email, authKey);

        user.ifPresent(selectUser ->{
            selectUser.setUserRole(UserRole.USER);
            userRepository.save(selectUser);
        });
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

    //마이페이지에서 비밀번호 변경
    @ApiOperation(value = "로그인된 유저 비밀번호 변경")
    @PostMapping("/changePw")
    public Object changePw(@RequestBody RequestChangePw request){
        ResponseBasic result = null;

        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;

            User currentUser = userRepository.findByEmail(userDetails.getUsername()).get();
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(currentUser);

            result = new ResponseBasic(true, "success", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "비밀번호 변경 실패", null);
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 로그인
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public Object login(@RequestBody RequestLoginUser user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();

        ResponseBasic result = new ResponseBasic();
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
            httpHeaders.set("accessToken", jwtUtil.generateToken(member));
        }
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(result);
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public Object login(HttpServletRequest request, HttpServletResponse response){
        ResponseBasic result = new ResponseBasic();

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null)
                new SecurityContextLogoutHandler().logout(request, response, auth);

            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            result = new ResponseBasic(false, "로그인 실패", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원정보 수정
    @ApiOperation(value = "회원정보 수정")
    @PutMapping("/updateInfo")
    public Object updateInfo(@RequestParam String email,@RequestParam String name) {
        ResponseBasic result = new ResponseBasic();

        Optional<User> userInfo = userRepository.findByEmail(email);

            if (userInfo.isPresent()) {
                userInfo.ifPresent(selectUser -> {
                    selectUser.setNickname(name);
                    userRepository.save(selectUser);
                });
                result.status = true;
                result.data = "success";
            } else {
                result.status = false;
                result.data = "회원정보 수정 실패";
            }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "프로필사진 수정을 위한 삭제")
    @DeleteMapping("/deleteImg")
    public Object deleteImg(@RequestParam Long userPk) {
        ResponseBasic result = new ResponseBasic();
        try {
            Optional<Profile> exImage = profileRepository.findByUserPk(userPk);

            if (exImage.isPresent()) {
                exImage.ifPresent(selectUser -> {
                    profileRepository.delete(selectUser);
                });
            }
            result.status = true;
            result.data = "profile delete success";
//            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "프로필사진 변경")
    @PostMapping("/insertImg")
    public Object insertImg(HttpServletRequest req, @RequestParam("image") MultipartFile files,@RequestParam Long userPk) {
        ResponseBasic result = new ResponseBasic();
        Profile profile = new Profile();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;

        String fileUrl = "C:/Users/HOME/IdeaProjects/images/";

        try {
            Optional<Profile> exImage = profileRepository.findByUserPk(userPk);

            if (exImage.isPresent()) {
                exImage.ifPresent(selectUser -> {
                    profileRepository.delete(selectUser);
                });
            }
            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);

            profile.setFileName(destinationFileName);
            profile.setFileOriname(sourceFileName);
            profile.setFileUrl(fileUrl);
            profile.setUserPk(userPk);
            profileRepository.save(profile);

            result.status = true;
            result.data = "profile upload success";
//            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 탈퇴
    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/deleteUser")
    public Object deleteUser(HttpServletRequest req) {
        ResponseBasic result = new ResponseBasic();

        try {
//            Map<String, Object> userMap = (Map<String, Object>) jwtUtil.getUser(req.getHeader("accessToken"));
//            System.out.println(userMap);
//            User user = userRepository.findByEmail((String) userMap.get("email")).get();
            User user = userRepository.findByEmail("gksgpals96@naver.com").get(); //Test
            Optional<User> userInfo = userRepository.findByEmail(user.getEmail());
            System.out.println(userInfo);

            userInfo.ifPresent(selectUser->{
                selectUser.setUserRole(UserRole.GUEST);
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
