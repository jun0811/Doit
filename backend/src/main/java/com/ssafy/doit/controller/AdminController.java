package com.ssafy.doit.controller;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.repository.AdminRepository;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    //관리자 - 회원 리스트
    @ApiOperation(value = "관리자 - 회원 리스트")
    @GetMapping("/searchAllUser")
    public Object searchAllUser(){ // 페이징 처리하기
        ResponseBasic result = new ResponseBasic();
        List<User> list = userRepository.findAll();

        if(list.size() == 0){
            result.status =false;
            result.data= "fail";
        }else{
            result.status = true;
            result.data = "success";
            result.object = list;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //관리자 - 회원 삭제
    @ApiOperation(value = "관리자 - 회원 탈퇴")
    @PutMapping("/beDeletedUser")
    public Object beDeletedUser(@RequestParam Long id) {
        ResponseBasic result = new ResponseBasic();
        try {
            Optional<User> userInfo = userRepository.findById(id);
            if (userInfo.isPresent()) {
                userInfo.ifPresent(selectUser -> {
                    selectUser.setUserRole(UserRole.GUEST);
                    userRepository.save(selectUser);
                });
            }
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


    //관리자 - 그룹 리스트
    @ApiOperation(value = "관리자 -그룹 리스트")
    @GetMapping("/searchAllGroup")
    public Object searchAllGroup(){ // 페이징 처리하기
        List<Group> list = groupRepository.findAll();
        ResponseBasic result = new ResponseBasic();
        if(list.size() == 0){
            result.status =false;
            result.data= "fail";
        }else{
            result.status = true;
            result.data = "success";
            result.object = list;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //그룹 삭제
//    froup has  어짜고 테이블@@@ 삭제 잘 보고 0--

    //피드 삭제
}
