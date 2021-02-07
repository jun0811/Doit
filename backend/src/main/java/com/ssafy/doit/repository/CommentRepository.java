package com.ssafy.doit.repository;

import com.ssafy.doit.model.Comment;
import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFeedPk(Long feedPk);

    Optional<Comment> findByCommentPk(Long commentPk);
}
