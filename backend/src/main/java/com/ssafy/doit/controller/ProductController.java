package com.ssafy.doit.controller;

import com.ssafy.doit.model.Mileage;
import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.request.RequestPage;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.response.ResponseProduct;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.MileageRepository;
import com.ssafy.doit.repository.ProductRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final String DEFAULT_CURRENT_PAGE = "1";
    private static final String DEFAULT_PER_PAGE = "9";
    private static final String DEFAULT_DIRECTION = "DESC";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MileageRepository mileageRepository;

    @ApiOperation(value = "물품 등록")
    @PostMapping("/createProduct")
    public Object createProduct(@RequestBody Product product){
        ResponseBasic result = null;
        try{
            long userPk = userService.currentUser();
            User currentUser = userRepository.findById(userPk).get();
            int groupCount = currentUser.getGroupList().size();

            if(groupCount < 2) throw new Exception("그룹 수 부족");

            product.setUser(currentUser);
            productRepository.save(product);
            result = new ResponseBasic(true, "success", product);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
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

            if(origin.getUser().getId() != currentUser) throw new Exception("유저 불일치");

            origin.setTitle(product.getTitle());
            origin.setContent(product.getContent());
            origin.setImage(product.getImage());
            origin.setCategory(product.getCategory());
            productRepository.save(origin);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
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

            if(origin.getUser().getId() != currentUser) throw new Exception("유저 불일치");

            productRepository.delete(origin);
            result = new ResponseBasic(true, "success", null);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "물품 상세 정보")
    @GetMapping("/{id}")
    public Object detail(@PathVariable Long id){
        ResponseBasic result = null;

        try{
            ResponseProduct product = productRepository.getById(id);
            result = new ResponseBasic(true, "success", product);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "물품 목록 조회")
    @GetMapping("/search")
    public Object search(@RequestParam(defaultValue = DEFAULT_CURRENT_PAGE) int pg,
                         @RequestParam(defaultValue = DEFAULT_DIRECTION) String direction,
                         @RequestParam(required = false) String option, @RequestParam(required = false) String keyword) {
        ResponseBasic result = null;
        try{
            RequestPage page = new RequestPage();
            page.setPage(pg);
            page.setSize(Integer.parseInt(DEFAULT_PER_PAGE));
            page.setDirection(direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC);

            Page<ResponseProduct> products;
            if("user".equals(option)) products = productRepository.findAllByUserNicknameContaining(keyword, page.of());
            else if("category".equals(option)) products = productRepository.findAllByCategory(keyword, page.of());
            else if("title".equals(option)) products = productRepository.findAllByTitleContaining(keyword, page.of());
            else products = productRepository.findAllBy(page.of());

            result = new ResponseBasic(true, "success", products);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

    @ApiOperation(value = "물품 판매")
    @GetMapping("/sell")
    public Object sell(@RequestParam Long pid, @RequestParam Long uid){
        ResponseBasic result = null;
        try{
            User host = userRepository.findById(userService.currentUser()).get();
            User consumer = userRepository.findById(uid).get();
            Product product = productRepository.findById(pid).get();

            if(host.getId() != product.getUser().getId()) throw new Exception("유저 불일치");
            if(consumer.getMileage() < product.getMileage()) throw new Exception("마일리지 부족");

            host.setMileage(host.getMileage() + product.getMileage());
            userRepository.save(host);
            mileageRepository.save(Mileage.builder()
                    .content("마일리지 상점 물품 판매")
                    .date(LocalDate.now())
                    .user(host).build());

            consumer.setMileage(consumer.getMileage() - product.getMileage());
            userRepository.save(consumer);
            mileageRepository.save(Mileage.builder()
                    .content("마일리지 상점 물품 판매")
                    .date(LocalDate.now())
                    .user(consumer).build());

            product.setStatus(false);
            productRepository.save(product);
            result = new ResponseBasic(true, "판매 완료", null);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
