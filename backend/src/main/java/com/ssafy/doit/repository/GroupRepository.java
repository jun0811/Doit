package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g where exists (select h from GroupHashTag h " +
            "where h.group = g and h.hashTag.name like %:hashtag% ) and g.status = :status")
    List<Group> findAllByHashTagAndStatus(@Param("hashtag") String hashtag, String status);
    Optional<Group> findByGroupPk(Long groupPk);
    List<Group> findAllByCategoryAndStatus(String category, String status);
}
