package com.ssafy.doit.controller;

import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.request.ResponseFeed;
import com.ssafy.doit.repository.FeedRepository;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequiredArgsConstructor
@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @GetMapping("/groupFeed")
    public Object groupFeedList(@RequestParam Long groupPk){
        List<Feed> list = feedRepository.findAll(groupPk);

        BasicResponse result = new BasicResponse();

        if(list.size() == 0){
            result.data = "해당 그룹의 피드가 존재하지 않습니다.";
            result.status = false;
        }else{
            result.data = "success";
            result.status = true;
            result.object = list;
        }
        //******인증 개수 70%이상인지 계산해서 auth_check 판별 해서 넘겨주기!!!
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
