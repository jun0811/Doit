package com.ssafy.doit.service;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.CommentRepository;
import com.ssafy.doit.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 한지현
 */
@Service
public class CommentService {
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void createComment(Long userPk, Long feedPk, Comment comment) throws Exception {

        Optional<Feed> opt = feedRepository.findById(comment.getFeedPk());

        if (!opt.isPresent()) {
            commentRepository.save(Comment.builder()
                    .content(comment.getContent())
                    .feedPk(feedPk)
                    .userPk(userPk)
                    .createDate(LocalDateTime.now()).build());
        }
    }

    public List<Comment> commentList(Long feedPk) {
        List<Comment> list = commentRepository.findAllByFeedPk(feedPk);
        return list;
    }

    @Transactional
    public void updateComment(Long userPk, Comment commentReq) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentReq.getCommentPk());
        if (userPk == comment.get().getUserPk()) {
            comment.ifPresent(selectComment -> {
                selectComment.setContent(commentReq.getContent());
                selectComment.setUpdateDate(LocalDateTime.now().toString());
                commentRepository.save(selectComment);
            });
        } else throw new Exception("댓글 작성자가 아닙니다.");
    }

    public void deleteComment(Long userPk, Long commentPk) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentPk);
        if (userPk == comment.get().getUserPk()) {
            comment.ifPresent(selectComment -> {
                commentRepository.delete(selectComment);
            });
        } else throw new Exception("댓글 작성자가 아닙니다.");
    }
}