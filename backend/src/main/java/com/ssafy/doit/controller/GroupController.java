package com.ssafy.doit.controller;

import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.service.GroupHashTagService;
import com.ssafy.doit.service.GroupUserService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private final UserService userService;
    @Autowired
    private GroupHashTagService groupHashTagService;
    @Autowired
    private GroupUserService groupUserService;

    // 그룹 생성
    @ApiOperation(value = "그룹 생성")
    @PostMapping("/createGroup")
    public Object createGroup(@RequestBody Group groupReq,
                              @RequestParam("hashtags") List<String> hashtags) {
        Long userPk = userService.currentUser();
        Long groupPk = groupHashTagService.create(userPk, groupReq, hashtags);
        groupUserService.join(userPk, groupPk);
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "그룹이 생성되었습니다.";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 정보 수정
    @ApiOperation(value = "그룹 수정")
    @PutMapping("/updateGroup")
    public Object updateGroup(Long groupPk, @RequestBody Group groupReq) {
        groupHashTagService.updateGroup(groupPk, groupReq);
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "그룹의 정보가 수정되었습니다.";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 추가
    @ApiOperation(value = "그룹 해시태그 추가")
    @PutMapping("/updateHashTag")
    public Object updateHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        groupHashTagService.updateHashTag(groupPk, hashtag);
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "해시태그가 추가되었습니다.";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 삭제
    @ApiOperation(value = "그룹 해시태그 삭제")
    @DeleteMapping("/deleteHashTag")
    public Object deleteHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        groupHashTagService.deleteHashTag(groupPk, hashtag);
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "해시태그가 삭제되었습니다.";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 리스트
    @ApiOperation(value = "그룹 리스트")
    @GetMapping("/searchGroup")
    public Object searchGroup(@RequestParam String tag){ // 페이징 처리하기
        List<ResponseGroup> list = groupHashTagService.findAllByHashTag(tag);
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

    // 선택한 그룹 정보 제공
    @ApiOperation(value = "선택한 그룹 정보 제공")
    @GetMapping("/detailGroup")
    public Object detailGroup(@RequestParam Long groupPk){
        ResponseGroup group = groupHashTagService.findByGroupPk(groupPk);
        ResponseBasic result = new ResponseBasic();
        if (group == null) {
            result.status = false;
            result.data = "fail";
        }else{
            result.status = true;
            result.data = "success";
            result.object = group;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 가입하기
    @ApiOperation(value = "그룹 가입하기")
    @GetMapping("/joinGroup")
    public Object joinGroup(@RequestParam Long groupPk){
        Long userPk = userService.currentUser();
        ResponseBasic result = new ResponseBasic();
        int opt = groupUserService.join(userPk,groupPk);
        if(opt == 0){
            result.status = true;
            result.data = "가입 완료되었습니다.";
        }else if(opt == 1){
            result.status = false;
            result.data = "이미 가입되어있는 그룹입니다.";
        }else if(opt == 2){
            result.status = false;
            result.data = "인원이 다 찼습니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 가입 그룹 리스트
    @ApiOperation(value = "가입 그룹 리스트")
    @GetMapping("/joinedGroup")
    public Object joinedGroup(@RequestParam Long userPk){
        List<ResGroupList> list = groupUserService.findGroupByUserPk(userPk);
        return getObject(list);
    }

    // 현재 로그인한 유저의 가입 그룹 리스트
    @ApiOperation(value = "현재 로그인한 유저의 가입 그룹 리스트")
    @GetMapping("/currentUserGroup")
    public Object currentUserGroup(){
        Long userPk = userService.currentUser();
        List<ResGroupList> list = groupUserService.findGroupByUserPk(userPk);
        return getObject(list);
    }

    private Object getObject(List<ResGroupList> list) {
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

}