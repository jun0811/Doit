package com.ssafy.doit.controller;

import com.ssafy.doit.model.CommitGroup;
import com.ssafy.doit.model.CommitUser;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.response.ResRanking;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.*;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final UserRepository userRepository;
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private HashTagRepository hashTagRepository;
    @Autowired
    private CommitUserRepository commitUserRepository;
    @Autowired
    private CommitGroupRepository commitGroupRepository;

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

    //@Scheduled(cron = "0 0 0 * * *") // 매일 00시 마다
    public void initCommitUser(){
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            LocalDate date = LocalDate.now();
            commitUserRepository.save(CommitUser.builder()
                    .date(date)
                    .userPk(user.getId())
                    .cnt(0)
                    .build());
        }
    }

    // 잔디에 보낼 커밋수
    @ApiOperation(value = "잔디에 보낼 커밋(인증수) 데이터")
    @GetMapping("/grass")
    public Object grass(Long userPk){
        ResponseBasic result = null;
        try {
            LocalDate today = LocalDate.now();
            LocalDate before = today.minusDays(29); //59
            List<CommitUser> list = commitUserRepository.findByUserPkAndDateBetweenOrderByDate(userPk,before,today);
            List<Integer> resGrass = new ArrayList<>();
            for(CommitUser cu : list){
                resGrass.add(cu.getCnt());
            }
            result = new ResponseBasic(true,"success", resGrass);
        }catch(Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false,"fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 일간 그룹 순위 => 메인 화면에 노출되는 api
    @ApiOperation(value = "일간 그룹 순위 => 메인 화면에 노출")
    @GetMapping("/rankingGroup")
    public Object rankingGroup(){
        ResponseBasic result = null;
        try {
            List<CommitGroup> cgList = commitGroupRepository.findTop5ByDateOrderByCalcDesc(LocalDate.now());
            List<ResRanking> resList = new ArrayList<>();
            int rank = 1;
            for(CommitGroup cg : cgList){
                String name = groupRepository.findById(cg.getGroupPk()).get().getName();
                resList.add(new ResRanking(rank++,cg.getGroupPk(),name));
            }
            result = new ResponseBasic(true,"success", resList);
        }catch(Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false,"fail", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 주간 그룹 순위 => 그룹점수와 개인 마일리지 점수를 제공하는 function
    // 매주 월요일마다 그 전주(7일)동안 각 그룹의 인증수 비율을 합한 수대로 순위를 매겨
    // 1~5위부터 그룹 점수 제공하며 각 그룹에 속해 있는 그룹원들에게 마일리지 점수 제공
    // ((group table에는 그 동안(주간 그룹 순위) 받은 누적 그룹 점수로 명예의 전당으로 활용 가능))
    // @Scheduled(cron = "0 0 0 * * MON") // 월요일 자정마다
    public void giveScore(){
        LocalDate start = LocalDate.now().minusDays(7);
        LocalDate end = LocalDate.now().minusDays(1);
        List<CommitGroup> cgList = commitGroupRepository.weekRanking(start, end);
        for(CommitGroup cg : cgList){
            // 점수 제공 구현 중
        }
    }
}
