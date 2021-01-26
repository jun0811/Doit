package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where exists (select h from GroupHashTag h " +
            "where h.group = g and h.hashTag.name = :hashtag)")
    List<Group> findAllByHashTag(@Param("hashtag") String hashtag);


    @Query("select g from Group g where exists (select gu from GroupUser gu " +
            "where gu.group = g and gu.user.id = :userPk)")
    List<Group> findAllByUserPk(@Param("userPk") Long userPk);
}
