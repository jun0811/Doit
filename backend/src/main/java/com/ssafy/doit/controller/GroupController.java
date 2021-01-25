package com.ssafy.doit.controller;

import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.service.GroupHashTagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupHashTagService groupHashTagService;

    @Autowired
    private final GroupRepository groupRepository;

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

    // 그룹 상세 정보

    // 그룹 가입하기

    // 가입한 그룹 리스트
}
