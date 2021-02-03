package com.ssafy.doit.repository;

import com.ssafy.doit.model.FeedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedUserRepository extends JpaRepository<FeedUser, Long> {
}
