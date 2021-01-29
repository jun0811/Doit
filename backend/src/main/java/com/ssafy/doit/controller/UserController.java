package com.ssafy.doit.controller;

import com.ssafy.doit.model.Profile;
import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.response.ResponseUser;
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
    private ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;


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

    // 로그인한 사용자 정보
    @ApiOperation(value = "로그인한 사용자 정보")
    @GetMapping("/detailUser")
    public Object detailUser(){
        ResponseBasic result = new ResponseBasic();

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            User getUser = userRepository.findByEmail(userDetails.getUsername()).get();
            ResponseUser user = new ResponseUser(getUser);

            result.status = true;
            result.data = "success";
            result.object = user;
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "fail";
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

    // 마이페이지에서 비밀번호 변경
    @ApiOperation(value = "로그인한 사용자 비밀번호 변경")
    @PostMapping("/changePw")
    public Object changePw(@RequestBody String requestPw){
        ResponseBasic result = null;

        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            User currentUser = userRepository.findByEmail(userDetails.getUsername()).get();
            currentUser.setPassword(passwordEncoder.encode(requestPw));
            userRepository.save(currentUser);

            result = new ResponseBasic(true, "success", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "비밀번호 변경 실패", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "프로필사진 변경")
    @PostMapping("/insertImg")
    public Object insertImg(HttpServletRequest req, @RequestParam("image") MultipartFile files) {
        ResponseBasic result = new ResponseBasic();
        Profile profile = new Profile();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;

        String fileUrl = "C:/";

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            User user = userRepository.findByEmail(userDetails.getUsername()).get();
            System.out.println(user.getId());
            Optional<Profile> userProfile = profileRepository.findByUserPk(user.getId());

            if (userProfile.isPresent()) {
                userProfile.ifPresent(selectUser -> {
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
            profile.setUserPk(user.getId());
            profileRepository.save(profile);

            result.status = true;
            result.data = "profile upload success";
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
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            User user = userRepository.findByEmail(userDetails.getUsername()).get();

            user.setUserRole(UserRole.GUEST);
            userRepository.save(user);

            result.status = true;
            result.data = "탈퇴 success";
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
