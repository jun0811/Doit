package com.ssafy.doit.controller;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.repository.CommentRepository;
import com.ssafy.doit.service.CommentService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/addComment")
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

    // 해당 피드 댓글 리스트
    @ApiOperation(value = "해당 피드 댓글 리스트")
    @GetMapping("/commentList")
    public Object commentList(@RequestParam Long feedPk){
        ResponseBasic result = null;
        try {
            List<Comment> list = commentService.commentList(feedPk);
            if(list.size() == 0) throw new Exception("댓글이 없습니다.");
            result = new ResponseBasic(true, "success", list);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "댓글 수정")
    @PutMapping("/updateComment")
    public Object updateComment(@RequestBody Comment comment){
        ResponseBasic result = null;
        try{
            Long userPk = userService.currentUser();
            System.out.println(userPk+ " " + comment.getUserPk());
            if(userPk == comment.getUserPk()) {
                commentService.updateComment(comment);
            }else throw new Exception("댓글 작성자가 아닙니다.");
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/deleteComment")
    public Object deleteComment(@RequestParam Long commentPk){
        ResponseBasic result = null;
        try {
            Long userPk = userService.currentUser();
            commentService.deleteComment(userPk, commentPk);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e) {
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
