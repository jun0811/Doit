package com.ssafy.doit.controller;

import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.Group;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupController {

    // 그룹 생성
    @ApiOperation(value = "그룹 생성")
    @PostMapping("/createGroup")
    public Object createGroup(@RequestBody Group request) {
        BasicResponse result = new BasicResponse();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 그룹 리스트

    // 그룹 상세 정보

    // 그룹 가입하기

    // 가입한 그룹 리스트
}
