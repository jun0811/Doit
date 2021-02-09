package com.ssafy.doit.repository;

import com.ssafy.doit.model.CommitGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CommitGroupRepository extends JpaRepository<CommitGroup, Long> {
    Optional<CommitGroup> findByGroupPkAndDate(Long groupPk, LocalDate now);
}
