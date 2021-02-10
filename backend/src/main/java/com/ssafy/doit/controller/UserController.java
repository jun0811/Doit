package com.ssafy.doit.controller;

import com.ssafy.doit.model.Mileage;
import com.ssafy.doit.model.request.RequestChangePw;
import com.ssafy.doit.model.response.ResponseUser;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.request.RequestLoginUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.MileageRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.GroupUserService;
import com.ssafy.doit.service.S3Service;
import com.ssafy.doit.service.UserService;
import com.ssafy.doit.service.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
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
    private final UserService userService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private MileageRepository mileageRepository;
    private final PasswordEncoder passwordEncoder;


    // 로그인
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public Object login(@RequestBody RequestLoginUser userReq) {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseBasic result = new ResponseBasic();

        Optional<User> userOpt = userRepository.findByEmail(userReq.getEmail());
        if(!userOpt.isPresent()){
            result = new ResponseBasic(false, "해당 이메일이 존재하지 않습니다.", null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            User user = userOpt.get();
            if(user.getUserRole().equals(UserRole.GUEST)){
                result = new ResponseBasic(false, "회원가입 이메일 인증 후 로그인 가능합니다.", null);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }else if(user.getUserRole().equals(UserRole.WITHDRAW)) {
                result = new ResponseBasic(false, "탈퇴한 회원으로 로그인 불가합니다.", null);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }else{
                if (!passwordEncoder.matches(userReq.getPassword(), user.getPassword())) {
                    result = new ResponseBasic(false, "잘못된 비밀번호입니다.", null);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }else {
                    String content = "로그인 마일리지 지급";
                    Optional<Mileage> opt = mileageRepository.findByContentAndDateAndUser(content, LocalDate.now(), user);
                    if(!opt.isPresent()){
                        user.setMileage(user.getMileage() + 50);
                        userRepository.save(user);
                        mileageRepository.save(Mileage.builder()
                                .content("로그인 마일리지 지급")
                                .date(LocalDate.now())
                                .user(user).build());
                    }
                    result = new ResponseBasic(true, "success", user);
                    httpHeaders.set("accessToken", jwtUtil.generateToken(user));
                }
            }
        }
        return ResponseEntity.ok().headers(httpHeaders).body(result);
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
    @ApiOperation(value = "로그인한 회원 정보")
    @GetMapping("/detailUser")
    public Object detailUser(){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            ResponseUser user = userService.detailUser(userPk);
            result = new ResponseBasic(true, "success", user);
        } catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원정보 수정
    @ApiOperation(value = "회원정보(닉네임) 수정")
    @PutMapping("/updateInfo")
    public Object updateInfo(@RequestBody User userReq) {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            Optional<User> user = userRepository.findById(userPk);

            user.ifPresent(selectUser->{
                selectUser.setNickname(userReq.getNickname());
                userRepository.save(selectUser);
            });
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "회원정보(프로필) 수정")
    @PostMapping("/updateImg")
    public Object updateImg(@RequestParam MultipartFile file) {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            User currentUser = userRepository.findById(userPk).get();
            String imgPath = s3Service.upload(currentUser.getImage(),file);

            currentUser.setImage(imgPath);
            userRepository.save(currentUser);
            result = new ResponseBasic(true, "success", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "프로필 사진 변경 fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 마이페이지에서 비밀번호 변경
    @ApiOperation(value = "로그인한 사용자 비밀번호 변경")
    @PostMapping("/changePw")
    public Object changePw(@RequestBody RequestChangePw requestPw){
        ResponseBasic result = new ResponseBasic();
        try {
            Long userPk = userService.currentUser();
            User currentUser = userRepository.findById(userPk).get();
            currentUser.setPassword(passwordEncoder.encode(requestPw.getPassword()));
            userRepository.save(currentUser);
            result = new ResponseBasic(true, "success", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "비밀번호 변경 실패", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 탈퇴
    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/deleteUser")
    public Object deleteUser() {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupUserService.deleteGroupByUser(userPk);
            result = new ResponseBasic(true, "success", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //    피드 리스트 공개 (feed_open)
    //    그룹 리스트 공개 (group_open)
    //    비공개 - 나만보기
    @ApiOperation(value = "회원 피드,그룹 리스트 공개/비공개")
    @PutMapping("/setOnAndOff")
    public Object setOnAndOff(@RequestParam String open ,@RequestParam String opt) {
        ResponseBasic result = new ResponseBasic();
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            User user = userRepository.findByEmail(userDetails.getUsername()).get();
            if(opt.equals("feed")) {
                user.setFeedOpen(open);
            }else if(opt.equals("group")) {
                user.setGroupOpen(open);
            }
            userRepository.save(user);

            result.status = true;
            result.data = "공개/비공개 설정정 success";
        }
        catch (Exception e){
            e.printStackTrace();
            result.status = false;
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
