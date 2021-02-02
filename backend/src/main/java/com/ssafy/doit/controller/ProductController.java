package com.ssafy.doit.controller;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.repository.ProductRepository;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @ApiOperation(value = "물품 등록")
    @PostMapping("/createProduct")
    public Object createProduct(@RequestBody Product product){
        ResponseBasic result = null;
        try{
            long currentUser = userService.currentUser();
            product.setUserPk(currentUser);
            productRepository.save(product);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            result = new ResponseBasic(false, "fail", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = )
}
