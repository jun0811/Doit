package com.ssafy.doit.controller;

import com.ssafy.doit.model.request.RequestGroup;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.service.GroupHashTagService;
import com.ssafy.doit.service.GroupUserService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Object createGroup(@RequestBody RequestGroup groupReq) {
        Long userPk = userService.currentUser();
        Long groupPk = groupHashTagService.create( userPk, groupReq);
        groupUserService.join(userPk, groupPk);

        ResponseBasic result = null;
        result = new ResponseBasic(true,"success",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 정보 수정
    @ApiOperation(value = "그룹 수정")
    @PutMapping("/updateGroup")
    public Object updateGroup(@RequestBody Group groupReq) {
        groupHashTagService.updateGroup(groupReq);
        ResponseBasic result = null;
        result = new ResponseBasic(true,"success",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 추가
    @ApiOperation(value = "그룹 해시태그 추가")
    @PutMapping("/updateHashTag")
    public Object updateHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        int opt = groupHashTagService.updateHashTag(groupPk, hashtag);
        ResponseBasic result = new ResponseBasic();
        if(opt == 1)
            result = new ResponseBasic(false,"fail",null);
        else if(opt == 0)
            result = new ResponseBasic(true,"success",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 삭제
    @ApiOperation(value = "그룹 해시태그 삭제")
    @DeleteMapping("/deleteHashTag")
    public Object deleteHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        groupHashTagService.deleteHashTag(groupPk, hashtag);
        ResponseBasic result = null;
        result = new ResponseBasic(true, "success", null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 리스트
    @ApiOperation(value = "그룹 리스트")
    @GetMapping("/searchGroup")
    public Object searchGroup(@RequestParam String tag){ // 페이징 처리하기
        List<ResponseGroup> list = groupHashTagService.findAllByHashTag(tag);
        ResponseBasic result = null;
        if(list.size() == 0) {
            result = new ResponseBasic(false, "fail", null);
        }else {
            result = new ResponseBasic(true, "success", list);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 선택한 그룹 정보 제공
    @ApiOperation(value = "선택한 그룹 정보 제공")
    @GetMapping("/detailGroup")
    public Object detailGroup(@RequestParam Long groupPk){
        ResponseGroup group = groupHashTagService.findByGroupPk(groupPk);
        ResponseBasic result = null;
        if (group == null) {
            result = new ResponseBasic(false,"fail",null);
        }else{
            result = new ResponseBasic(true,"success",group);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 가입하기
    @ApiOperation(value = "그룹 가입하기")
    @GetMapping("/joinGroup")
    public Object joinGroup(@RequestParam Long groupPk){
        Long userPk = userService.currentUser();
        ResponseBasic result = null;
        int opt = groupUserService.join(userPk,groupPk);
        if(opt == 0){
            result = new ResponseBasic(true,"가입 완료되었습니다.",null);
        }else if(opt == 1){
            result = new ResponseBasic(false,"이미 가입된 그륩입니다.",null);
        }else if(opt == 2){
            result = new ResponseBasic(false,"인원이 가득 찼습니다.",null);
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
        ResponseBasic result = null;
        if(list.size() == 0){
            result = new ResponseBasic(false,"fail",null);
        }else{
            result = new ResponseBasic(true,"success",list);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}