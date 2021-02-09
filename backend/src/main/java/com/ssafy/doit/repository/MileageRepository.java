package com.ssafy.doit.repository;

import com.ssafy.doit.model.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage,Long> {
}
