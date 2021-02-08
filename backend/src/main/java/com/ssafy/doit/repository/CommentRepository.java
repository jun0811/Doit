package com.ssafy.doit.repository;

import com.ssafy.doit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFeedPk(Long feedPk);
}
