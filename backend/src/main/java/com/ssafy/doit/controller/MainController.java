package com.ssafy.doit.controller;

import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.HashTagRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private HashTagRepository hashTagRepository;

    @ApiOperation(value = "WordCloud cnt 내림차순 list")
    @GetMapping("/wordCloud")
    public Object wordCloud(){ // 페이징 처리하기
        ResponseBasic result = new ResponseBasic();
        List<HashTag> list = hashTagRepository.findAllByOrderByCntDesc();

        if(list.size() == 0){
            result.status =false;
            result.data= "fail";
        }else{
            result.status = true;
            result.data = "success";
            result.object = list;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
