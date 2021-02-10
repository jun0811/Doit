package com.ssafy.doit.repository;

import com.ssafy.doit.model.Mileage;
import com.ssafy.doit.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MileageRepository extends JpaRepository<Mileage,Long> {
    Optional<Mileage> findByContentAndDateAndUser(String content, LocalDate now, User user);
}
