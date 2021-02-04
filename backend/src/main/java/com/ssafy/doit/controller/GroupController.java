package com.ssafy.doit.controller;

import com.ssafy.doit.model.request.RequestGroup;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResGroupInfo;
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

/***
 * @author : 김부희
 */
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

    //해시태그에 따른 그룹 리스트
    @ApiOperation(value = "해시태그에 따른 그룹 리스트")
    @GetMapping("/searchGroup")
    public Object searchGroup(@RequestParam String tag) { // 페이징 처리하기
        ResponseBasic result = null;
        try {
            List<ResponseGroup> list = groupHashTagService.findAllByHashTag(tag);
            if(list.size() == 0) throw new Exception("해당 해시태그를 포함한 그룹이 없습니다.");
            result = new ResponseBasic(true, "success", list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 카테고리에 따른 그룹 리스트
    @ApiOperation(value = "카테고리에 따른 그룹 리스트")
    @GetMapping("/categoryGroup")
    public Object categoryGroup(@RequestParam String category) { // 페이징 처리하기
        ResponseBasic result = null;
        try {
            List<ResponseGroup> list = groupHashTagService.findAllByCategory(category);
            if(list.size() == 0)
                throw new Exception("해당 카테고리와 관련된 그룹이 아직 생성되지 않았습니다.");
            result = new ResponseBasic(true, "success", list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 선택한 그룹 정보 제공
    @ApiOperation(value = "선택한 그룹 정보 제공")
    @GetMapping("/detailGroup")
    public Object detailGroup(@RequestParam Long groupPk){
        ResponseBasic result = null;
        try {
            ResGroupInfo group = groupHashTagService.findByGroupPk(groupPk);
            if(group == null) throw new Exception("그룹을 찾을 수 없습니다.");
            result = new ResponseBasic(true,"success",group);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // 그룹 생성
    @ApiOperation(value = "그룹 생성")
    @PostMapping("/createGroup")
    public Object createGroup(@RequestBody RequestGroup groupReq) {
        ResponseBasic result = null;
        try{
            Long userPk = userService.currentUser();
            Long groupPk = groupHashTagService.create( userPk, groupReq);
            groupUserService.join(userPk, groupPk);
            result = new ResponseBasic(true,"success",null);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 정보 수정
    @ApiOperation(value = "그룹 수정")
    @PutMapping("/updateGroup")
    public Object updateGroup(@RequestBody Group groupReq) {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupHashTagService.updateGroup(userPk, groupReq);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 추가
    @ApiOperation(value = "그룹 해시태그 추가")
    @PutMapping("/updateHashTag")
    public Object updateHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupHashTagService.updateHashTag(userPk, groupPk, hashtag);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 해시태그 삭제
    @ApiOperation(value = "그룹 해시태그 삭제")
    @DeleteMapping("/deleteHashTag")
    public Object deleteHashTag(Long groupPk, @RequestParam("hashtag") String hashtag) {
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupHashTagService.deleteHashTag(userPk, groupPk, hashtag);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 가입 그룹 리스트
    @ApiOperation(value = "가입 그룹 리스트")
    @GetMapping("/joinedGroup")
    public Object joinedGroup(@RequestParam Long userPk){
        ResponseBasic result = null;
        try {
            List<ResGroupList> list = groupUserService.findGroupByUserPk(userPk);
            if(list.size() == 0) throw new Exception("가입한 그룹이 없습니다.");
            result = new ResponseBasic(true,"success",list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 현재 로그인한 유저의 가입 그룹 리스트
    @ApiOperation(value = "현재 로그인한 유저의 가입 그룹 리스트")
    @GetMapping("/currentUserGroup")
    public Object currentUserGroup(){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            List<ResGroupList> list = groupUserService.findGroupByUserPk(userPk);
            if(list.size() == 0) throw new Exception("가입한 그룹이 없습니다.");
            result = new ResponseBasic(true,"success",list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 가입하기
    @ApiOperation(value = "그룹 가입하기")
    @GetMapping("/joinGroup")
    public Object joinGroup(@RequestParam Long groupPk){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupUserService.join(userPk, groupPk);
            result = new ResponseBasic(true, "가입 완료되었습니다.", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 탈퇴하기
    @ApiOperation(value = "그룹 탈퇴하기")
    @DeleteMapping("/withdrawGroupUser")
    public Object withdrawGroupUser(@RequestParam Long groupPk){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            groupUserService.withdrawGroupUser(userPk, groupPk);
            result = new ResponseBasic(true,"success",null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 내 그룹원 강퇴시키기
    @ApiOperation(value = "그룹 내 그룹원 강퇴시키기")
    @DeleteMapping("/kickOutUser")
    public Object kickOutGroupUser(@RequestParam Long groupPk, @RequestParam Long userPk){
        ResponseBasic result = null;
        try {
            Long leader = userService.currentUser(); //로그인된 회원 - 그룹 리더
            groupUserService.kickOutGroupUser(userPk, groupPk, leader);
            result = new ResponseBasic(true,"success",null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}