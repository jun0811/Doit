package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findAllByGroup(Long groupPk);

}
