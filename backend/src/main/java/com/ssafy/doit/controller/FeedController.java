package com.ssafy.doit.controller;

import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.repository.FeedRepository;
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

    //해당 그룹 내 활동 피드
    @ApiOperation(value = "그룹 내 활동 피드")
    @GetMapping("/groupFeed")
    public Object groupFeedList(@RequestParam Long groupPk){
        ResponseBasic result = null;
        List<ResponseFeed> list = feedService.getFeedList(groupPk);
        if(list.size() == 0){
            result = new ResponseBasic(false, "해당 그룹의 피드가 존재하지 않습니다.", null);
        }else{
            result = new ResponseBasic(true, "success", list);
        }
        //******인증 개수 70%이상인지 계산해서 auth_check 판별 해서 넘겨주기!!!
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 그룹 내 피드 생성
    @ApiOperation(value = "그룹 내 피드 생성")
    @PostMapping("/createFeed")
    public Object createFeed(@RequestBody Feed feedReq){
        ResponseBasic result = null;
        try{
            Long userPk = userService.currentUser();
            feedService.create(userPk,feedReq);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            result = new ResponseBasic(false, "피드 생성 실패", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
