package com.ssafy.doit.controller;

import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.service.GroupHashTagService;
import com.ssafy.doit.service.GroupUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupHashTagService groupHashTagService;

    @Autowired
    private GroupUserService groupUserService;

    // 그룹 생성
    @ApiOperation(value = "그룹 생성")
    @PostMapping("/createGroup")
    //public Object createGroup(Group groupReq, @RequestParam("hashtags") List<String> hashtags) {
    public Object createGroup(HttpServletRequest userReq, @RequestBody Group groupReq,
                              @RequestParam("hashtags") List<String> hashtags) {
        Long groupPk = groupHashTagService.save(userReq, groupReq, hashtags);
        ResponseBasic result = new ResponseBasic();
        result.status = true;
        result.data = "그룹이 생성되었습니다.";
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
            result.data= "입력한 해시태그를 가진 그룹이 없습니다.";
        }else{
            result.status = true;
            result.data = "success";
            result.object = list;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 가입하기
    @ApiOperation(value = "그룹 가입하기")
    @GetMapping("/joinGroup")
    public Object joinGroup(HttpServletRequest userReq, @RequestParam Long groupPk){
        ResponseBasic result = new ResponseBasic();
        int opt = groupUserService.join(userReq, groupPk);
        if(opt == 0){
            result.status = true;
            result.data = "success";
        }else if(opt == 1){
            result.status = false;
            result.data = "이미 가입되어있는 그룹입니다.";
        }else if(opt == 2){
            result.status = false;
            result.data = "인원이 다 찼습니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 가입한 그룹 리스트
    @ApiOperation(value = "가입한 그룹 리스트")
    @GetMapping("/joinedGroup")
    public Object joinedGroup(HttpServletRequest userReq){
        //userPk로 가입된 groupPK -> 그룹 명 가져오기
        List<Group> list = groupUserService.findAllByUserPk(userReq);
        ResponseBasic result = new ResponseBasic();
        if(list.size() == 0){
            result.status =false;
            result.data= "가입된 그룹이 없습니다.";
        }else{
            result.status = true;
            result.data = "success";
            result.object = list;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}