package com.ssafy.doit.repository;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupHashTagRepository extends JpaRepository<GroupHashTag, Long> {
    Optional<GroupHashTag> findByGroupAndHashTag(Group group, HashTag HashTag);

    List<GroupHashTag> findAllByGroup(Group group);
}
