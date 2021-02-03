package com.ssafy.doit.service;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.response.ResponseProduct;
import com.ssafy.doit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProudctService {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public void createProduct(Product product) throws Exception{
//        Long currentUser = userService.currentUser();
//        product.setUserPk(currentUser);
//        productRepository.save(product);
//    }
//
//    public List<ResponseProduct> getAll() throws Exception{
//        List<ResponseProduct> res = new ArrayList<>();
//        List<Product> products = productRepository.findAll();
//
//    }
}
