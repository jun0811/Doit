package com.ssafy.doit.repository.feed;

import com.ssafy.doit.model.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(value = "select f from Feed f where f.groupPk = :groupPk and f.status = :status " +
            "and substring(f.createDate, 1, 10) between :startDate and :endDate order by f.createDate desc ")
    List<Feed> findAllByGroupPkAndStatusAndCreateDateBetween(Long groupPk, String status, String startDate, String endDate);

    @Query(value = "select f from Feed f where f.writer = :userPk " +
            "and substring(f.createDate, 1, 10) = :now")
    Optional<Feed> findByWriterAndCreateDate(Long userPk, String now);

    List<Feed> findAllByWriterAndStatusOrderByCreateDateDesc(Long userPk, String aTrue);
    List<Feed> findByGroupPkAndWriter(Long groupPk, Long userPk);
    List<Feed> findByWriter(Long userPk);
    List<Feed> findByGroupPk(Long groupPk);
}