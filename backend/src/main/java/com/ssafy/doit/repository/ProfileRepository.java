package com.ssafy.doit.repository;

import com.ssafy.doit.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.file.Files;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

       Optional<Profile> findByUserPk(Long userPk);

}
