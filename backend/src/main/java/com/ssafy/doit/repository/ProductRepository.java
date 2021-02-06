package com.ssafy.doit.repository;

import com.ssafy.doit.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTitleContaining(String title);
    List<Product> findAllByUserNicknameContaining(String nickname);
    List<Product> findAllByCategory(String category);
}
