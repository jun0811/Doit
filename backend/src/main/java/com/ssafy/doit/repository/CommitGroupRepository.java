package com.ssafy.doit.repository;

import com.ssafy.doit.model.CommitGroup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommitGroupRepository extends JpaRepository<CommitGroup, Long> {
    Optional<CommitGroup> findByGroupPkAndDate(Long groupPk, LocalDate now);
    List<CommitGroup> findTop5ByDateOrderByCalcDesc(LocalDate date);

    @Query(value = "select * from commit_group where date between ?1 " +
            "and ?2 group by group_pk order by sum(calc) desc limit 5",nativeQuery = true)
    List<CommitGroup> weekRanking(LocalDate start, LocalDate end);
}
