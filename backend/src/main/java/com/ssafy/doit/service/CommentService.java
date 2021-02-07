package com.ssafy.doit.service;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.CommentRepository;
import com.ssafy.doit.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void addComment(Long userPk, Long feedPk,Comment comment) throws Exception {

        Optional<Feed> opt = feedRepository.findByFeedPk(comment.getFeedPk());

        if(!opt.isPresent()) {
            commentRepository.save(Comment.builder()
                    .content(comment.getContent())
                    .feedPk(feedPk)
                    .userPk(userPk)
                    .createDate(LocalDateTime.now()).build());
        }
    }

}
