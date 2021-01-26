package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    Optional<GroupUser> findByGroupAndUser(Group group, User user);
}
