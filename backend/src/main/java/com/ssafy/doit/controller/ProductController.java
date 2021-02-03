package com.ssafy.doit.controller;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.response.ResponseProduct;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.ProductRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            e.printStackTrace();
            result = new ResponseBasic(false, "fail", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "물품 수정")
    @PutMapping("/{id}")
    public Object modifyProduct(@PathVariable Long id, @RequestBody Product product){
        ResponseBasic result = null;
        try{
            long currentUser = userService.currentUser();
            Product origin = productRepository.findById(id).get();

            if(origin.getUserPk() != currentUser) throw new Exception("유저 불일치");

            origin.setTitle(product.getTitle());
            origin.setContent(product.getContent());
            origin.setImage(product.getImage());
            origin.setCategory(product.getCategory());
            productRepository.save(origin);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "fail", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "물품 삭제")
    @DeleteMapping("/{id}")
    public Object createProduct(@PathVariable Long id){
        ResponseBasic result = null;
        try{
            long currentUser = userService.currentUser();
            Product origin = productRepository.findById(id).get();

            if(origin.getUserPk() != currentUser) throw new Exception("유저 불일치");

            productRepository.delete(origin);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            result = new ResponseBasic(false, "fail", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "물품 상세 정보")
    @GetMapping("/{id}")
    public Object detail(@PathVariable Long id){
        ResponseBasic result = null;

        try{
            ResponseProduct product = productRepository.getById(id);
            result = new ResponseBasic(true, "", product);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 물품 조회")
    @GetMapping("/getAll")
    public Object getAll(){
        ResponseBasic result = null;
        try{
            List<ResponseProduct> products = productRepository.findAllBy();
            result = new ResponseBasic(true, "", products);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "글제목 검색에 의한 물품 조회")
    @GetMapping("/searchTitle")
    public Object searchProduct(@RequestParam String title){
        ResponseBasic result = null;
        try{
            List<ResponseProduct> products = productRepository.findByTitleContaining(title);
            result = new ResponseBasic(true, "", products);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "유저 검색에 의한 물품 조회")
    @GetMapping("/searchUser")
    public Object searchUser(@RequestParam String nickname){
        ResponseBasic result = null;
        try{
            List<ResponseProduct> products = productRepository.findAllByUserNicknameContaining(nickname);
            result = new ResponseBasic(true, "", products);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 검색을 통한 물품 조회")
    @GetMapping("/searchCategory")
    public Object searchCategory(@RequestParam String category){
        ResponseBasic result = null;
        try{
            List<ResponseProduct> products = productRepository.findAllByCategory(category);
            result = new ResponseBasic(true, "", products);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, "", null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
