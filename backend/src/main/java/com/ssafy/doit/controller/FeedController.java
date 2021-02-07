package com.ssafy.doit.controller;

import com.ssafy.doit.model.response.ResMyFeed;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.service.FeedService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = ResponseBasic.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ResponseBasic.class),
        @ApiResponse(code = 404, message = "Not Found", response = ResponseBasic.class),
        @ApiResponse(code = 500, message = "Failure", response = ResponseBasic.class) })

@RequiredArgsConstructor
@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final FeedService feedService;

    // 그룹 내 피드 생성
    @ApiOperation(value = "그룹 내 피드 생성")
    @PostMapping("/createFeed")
    public Object createFeed(@RequestBody Feed feedReq){
        ResponseBasic result = null;
        try{
            Long userPk = userService.currentUser();
            int res = feedService.createFeed(userPk,feedReq);
            if(res == 1)
                result = new ResponseBasic(true, "success", null);
            else if(res == 0)
                result = new ResponseBasic(false, "오늘 이미 인증피드를 작성하였습니다.", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 내 피드 리스트
    @ApiOperation(value = "그룹 내 피드 리스트")
    @GetMapping("/groupFeed")
    public Object groupFeedList(@RequestParam Long groupPk, @RequestParam String date){
        ResponseBasic result = null;
        try {
            List<ResponseFeed> list = feedService.groupFeedList(groupPk, date);
            result = new ResponseBasic(true, "success", list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 개인 피드 리스트 (+ 다른유저 피드리스트)
    @ApiOperation(value = "개인 피드 리스트 (+ 다른유저 피드리스트)")
    @PostMapping("/userFeed")
    public Object userFeed(Long userPk, @RequestParam String date){
        ResponseBasic result = null;
        try {
            List<ResMyFeed> list = feedService.userFeedList(userPk, date);
            result = new ResponseBasic(true, "success", list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 개인 피드 수정
    @ApiOperation(value = "개인 피드 수정")
    @PutMapping("/updateFeed")
    public Object updateFeed(@RequestBody Feed feedReq){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            feedService.updateFeed(userPk, feedReq);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 개인 피드 삭제
    @ApiOperation(value = "개인 피드 삭제")
    @DeleteMapping("/deleteFeed")
    public Object deleteFeed(Long feedPk){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            feedService.deleteFeed(userPk, feedPk);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 인증피드 인증확인
    @ApiOperation(value = "인증피드 인증확인")
    @GetMapping("/authCheckFeed")
    public Object authCheckFeed(Long feedPk){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            feedService.authCheckFeed(userPk,feedPk);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}