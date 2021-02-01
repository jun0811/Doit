package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.model.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where exists (select h from GroupHashTag h " +
            "where h.group = g and h.hashTag.name = :hashtag)")
    List<Group> findAllByHashTag(@Param("hashtag") String hashtag);

    Optional<Group> findByGroupPk(Long groupPk);
}
