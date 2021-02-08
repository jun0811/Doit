package com.ssafy.doit.repository;

import com.ssafy.doit.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(value = "select f from Feed f where f.groupPk = :groupPk " +
            "and substring(f.createDate, 1, 10) = :date and f.status = :status", nativeQuery = true)
    List<Feed> findAllByGroupPkAndCreateDateAndStatus(Long groupPk, String date, String status);

    @Query(value = "select f from Feed f where f.writer= :userPk " +
            "and substring(f.createDate, 1, 10) = :date and f.status = :status", nativeQuery = true)
    List<Feed> findAllByWriterAndCreateDateAndStatus(Long userPk, String date, String status);

    @Query(value = "select f from Feed f where f.writer = :userPk " +
            "and substring(f.createDate, 1, 10) = :now", nativeQuery = true)
    Optional<Feed> findByWriterAndCreateDate(Long userPk, String now);

    List<Feed> findByGroupPkAndWriter(Long groupPk, Long userPk);
    List<Feed> findByWriter(Long userPk);
    List<Feed> findByGroupPk(Long groupPk);
}