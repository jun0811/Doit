package com.ssafy.doit.controller;

import com.ssafy.doit.model.request.RequestGroup;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.repository.GroupRepository;
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
import java.util.Optional;

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
    @Autowired
    private GroupRepository groupRepository;

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
        ResponseBasic result = null;
        Long userPk = userService.currentUser();
        int res = groupHashTagService.updateGroup(userPk, groupReq);
        if(res == 1)
            result = new ResponseBasic(true,"success",null);
        else if(res == 0)
            result = new ResponseBasic(false,"fail",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 추가
    @ApiOperation(value = "그룹 해시태그 추가")
    @PutMapping("/updateHashTag")
    public Object updateHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        ResponseBasic result = new ResponseBasic();
        Long userPk = userService.currentUser();
        int res = groupHashTagService.updateHashTag(userPk, groupPk, hashtag);
        if(res == 1)
            result = new ResponseBasic(true,"success",null);
        else if(res == 0)
            result = new ResponseBasic(false,"fail",null);
        else if(res == 2)
            result = new ResponseBasic(false,"해시태그가 이미 존재합니다.",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 삭제
    @ApiOperation(value = "그룹 해시태그 삭제")
    @DeleteMapping("/deleteHashTag")
    public Object deleteHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        ResponseBasic result = new ResponseBasic();
        Long userPk = userService.currentUser();
        int res = groupHashTagService.deleteHashTag(userPk, groupPk, hashtag);
        if(res == 1)
            result = new ResponseBasic(true,"success",null);
        else if(res == 0)
            result = new ResponseBasic(false,"fail",null);
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
        if(opt == 1){
            result = new ResponseBasic(true,"가입 완료되었습니다.",null);
        }else if(opt == 0){
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
    @ApiOperation(value = "그룹 내 그룹원 강퇴시키기")
    @DeleteMapping("/beDeletedGroupUser")
    public Object beDeletedGroup(@RequestParam Long groupPk,@RequestParam Long userPk){
        ResponseBasic result = null;
        Long leader = userService.currentUser(); //로그인된 회원 - 그룹 리더

        int res = groupUserService.beDeletedGroupUser(userPk, groupPk, leader);
        if(res == 1)
            result = new ResponseBasic(true,"success",null);
        else if(res == 0)
            result = new ResponseBasic(false,"fail",null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}