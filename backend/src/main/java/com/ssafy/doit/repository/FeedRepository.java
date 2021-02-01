package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("select f from Feed as f where f.groupPk = ?1")
    List<Feed> findAll(Long groupPk);

    Optional<Feed> findByFeedPk(Long feedPk);
}
