package com.ssafy.doit.repository;

import com.ssafy.doit.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Files;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
//    Optional<Profile> findByProfilePk(Long profilePk);
}
