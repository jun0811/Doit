package com.ssafy.doit.repository;

import com.ssafy.doit.model.notification.NotiType;
import com.ssafy.doit.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotiRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUserPkAndNotiTypeAndStatusIsTrue(Long uid, NotiType type);
    List<Notification> findAllByUserPkAndStatusIsTrue(Long uid);
}
