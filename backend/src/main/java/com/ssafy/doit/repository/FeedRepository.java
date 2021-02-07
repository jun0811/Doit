package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select f from Feed f where f.groupPk = :groupPk " +
            "and substring(f.createDate, 1, 10) = :date and f.status = :status")
    List<Feed> findAllByGroupPkAndCreateDateAndStatus(Long groupPk, String date, String status);
    @Query("select f from Feed f where f.writer= :userPk " +
            "and substring(f.createDate, 1, 10) = :date and f.status = :status")
    List<Feed> findAllByWriterAndCreateDateAndStatus(Long userPk, String date, String status);
    @Query("select f from Feed f where f.writer = :userPk and substring(f.createDate, 1, 10) = :now")
    Optional<Feed> findByWriterAndCreateDate(Long userPk, String now);
}
