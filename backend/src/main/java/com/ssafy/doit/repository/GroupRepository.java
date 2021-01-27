package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.response.ResponseGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where exists (select h from GroupHashTag h " +
            "where h.group = g and h.hashTag.name = :hashtag)")
    List<Group> findAllByHashTag(@Param("hashtag") String hashtag);
}
