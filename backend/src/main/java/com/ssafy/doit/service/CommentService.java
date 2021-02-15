package com.ssafy.doit.service;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.response.ResComment;
import com.ssafy.doit.model.response.ResMyFeed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.*;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final GroupUserRepository groupUserRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void createComment(Long userPk, Comment comment) throws Exception {

        Feed feed = feedRepository.findById(comment.getFeedPk()).get();
        Group group = groupRepository.findById(feed.getGroupPk()).get();
        User user = userRepository.findById(userPk).get();

        Optional<GroupUser> optGU = groupUserRepository.findByGroupAndUser(group,user);
        if(!optGU.isPresent()) throw new Exception("해당 그룹에 가입되어 있지 않아 접근 불가합니다.");

        commentRepository.save(Comment.builder()
            .content(comment.getContent())
            .feedPk(comment.getFeedPk())
            .userPk(userPk)
            .createDate(LocalDateTime.now()).build());
    }
    @Transactional
    public List<ResComment> commentList(Long feedPk) {
        List<Comment> list = commentRepository.findByFeedPk(feedPk);
        //            유저 이미지랑 닉네임도 같이 넘겨주실수있나용?
        List<ResComment> resList = new ArrayList<>();

        for(Comment comment : list){
            Long user = userRepository.findById(comment.getUserPk()).get().getId();
            String userImg = userRepository.findById(comment.getUserPk()).get().getImage();
            resList.add(new ResComment(comment, user, userImg));
        }
        return resList;
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