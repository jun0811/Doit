package com.ssafy.doit.controller;

import com.ssafy.doit.model.BasicResponse;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.request.ResponseFeed;
import com.ssafy.doit.repository.FeedRepository;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Feed> groupFeedList(@RequestParam Long groupPk){
        List<Feed> list = feedRepository.findAll(groupPk);

        return list;
    }
}
