package com.ssafy.doit.controller;

import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.service.GroupHashTagService;
import com.ssafy.doit.service.GroupUserService;
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
    private GroupHashTagService groupHashTagService;

    @Autowired
    private GroupUserService groupUserService;

    // 그룹 생성
    @ApiOperation(value = "그룹 생성")
    @PostMapping("/createGroup")
    //public Object createGroup(HttpServletRequest userReq, Group groupReq, List<String> hashtagList) {
    public Object createGroup(@RequestBody Group groupReq,
                              @RequestParam("hashtags") List<String> hashtags) {
        BasicResponse result = new BasicResponse();
        groupHashTagService.save(groupReq, hashtags);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 리스트
    @ApiOperation(value = "그룹 리스트")
    @GetMapping("/searchGroup")
    public Object searchGroup(@RequestParam String tag){ // 페이징 처리하기
        List<Group> list = groupHashTagService.findAllByHashTag(tag);
        BasicResponse result = new BasicResponse();
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
    @PostMapping("/joinedGroup")
    //public Object joinedGroup(Authentication authentication, Group groupReq)
    public Object joinedGroup(@RequestParam Long groupPk){
        BasicResponse result = new BasicResponse();
        groupUserService.join(groupPk);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 가입한 그룹 리스트
}
