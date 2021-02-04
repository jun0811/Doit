package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByGroupPkAndStatus(Long groupPk, String status);
    List<Feed> findAllByWriterAndStatus(Long userPk, String status);
}
