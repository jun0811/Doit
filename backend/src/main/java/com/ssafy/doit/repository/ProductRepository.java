package com.ssafy.doit.repository;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.response.ResponseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ResponseProduct> findAllByTitleContaining(String title);
    List<ResponseProduct> findAllBy();
    List<ResponseProduct> findAllByUserNicknameContaining(String nickname);
    List<ResponseProduct> findAllByCategory(String Category);
    ResponseProduct getById(Long id);

}
