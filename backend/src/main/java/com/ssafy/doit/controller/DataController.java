package com.ssafy.doit.controller;

import com.ssafy.doit.model.CommitUser;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.repository.CommitUserRepository;
import com.ssafy.doit.repository.HashTagRepository;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private final UserService userService;
    @Autowired
    private HashTagRepository hashTagRepository;
    @Autowired
    private CommitUserRepository commitUserRepository;

    // 해시태그 가장 많이 사용된 순서대로 데이터 제공
    @ApiOperation(value = "WordCloud 해시태그 데이터")
    @GetMapping("/wordCloud")
    public Object wordCloud(){
        ResponseBasic result = null;
        try {
            List<HashTag> list = hashTagRepository.findAll();
            Map<String, Integer> resWC = new HashMap<>();
            for (HashTag ht : list) {
                resWC.put(ht.getName(), ht.getCnt());
            }
            result = new ResponseBasic(true,"success", resWC);
        }catch(Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false,"fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
