package com.ssafy.doit.repository;
import com.ssafy.doit.model.CommitUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommitUserRepository extends JpaRepository<CommitUser, Long> {
    Optional<CommitUser> findByUserPkAndDate(Long userPk, LocalDate now);
    List<CommitUser> findByUserPk(Long userPk);
}
