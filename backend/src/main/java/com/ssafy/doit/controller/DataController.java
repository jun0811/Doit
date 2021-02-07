package com.ssafy.doit.controller;

import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.repository.HashTagRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private HashTagRepository hashTagRepository;

    // 해시태그 가장 많이 사용된 순서대로 데이터 제공
    @ApiOperation(value = "WordCloud cnt 내림차순 list")
    @GetMapping("/wordCloud")
    public Object wordCloud(){
        ResponseBasic result = null;
        try {
            List<HashTag> list = hashTagRepository.findAllByCnt();
            Map<String, Integer> word = new HashMap<>();
            for (HashTag ht : list) {
                word.put(ht.getName(), ht.getCnt());
            }
            result = new ResponseBasic(true,"success", word);
        }catch(Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false,"fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
