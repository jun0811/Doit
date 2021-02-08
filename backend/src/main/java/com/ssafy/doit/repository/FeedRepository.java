package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(value = "select f from Feed f where f.groupPk = :groupPk and f.status = :status " +
            "and substring(f.createDate, 1, 10) BETWEEN :startDate AND :endDate")
    List<Feed> findAllByGroupPkAndStatusAndCreateDateBetween(Long groupPk, String status, String startDate, String endDate);

    @Query(value = "select f from Feed f where f.writer= :userPk  and f.status = :status " +
            "and substring(f.createDate, 1, 10) BETWEEN :startDate AND :endDate")
    List<Feed> findAllByWriterAndStatusAndCreateDateBetween(Long userPk, String status, String startDate, String endDate);

    @Query(value = "select f from Feed f where f.writer = :userPk " +
            "and substring(f.createDate, 1, 10) = :now")
    Optional<Feed> findByWriterAndCreateDate(Long userPk, String now);

    List<Feed> findByGroupPkAndWriter(Long groupPk, Long userPk);
    List<Feed> findByWriter(Long userPk);
    List<Feed> findByGroupPk(Long groupPk);
}