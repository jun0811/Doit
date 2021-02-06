package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.FeedUser;
import com.ssafy.doit.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedUserRepository extends JpaRepository<FeedUser, Long> {
    Optional<FeedUser> findByFeedAndUser(Feed feed, User user);
}
