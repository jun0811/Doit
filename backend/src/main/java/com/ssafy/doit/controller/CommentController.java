package com.ssafy.doit.controller;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.repository.CommentRepository;
import com.ssafy.doit.service.CommentService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/comment")
    public Object addComment(@RequestParam Long feedPk,@RequestBody Comment comment){
        ResponseBasic result = null;
        try{
            Long userPk = userService.currentUser();
            commentService.addComment(userPk,feedPk,comment);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
