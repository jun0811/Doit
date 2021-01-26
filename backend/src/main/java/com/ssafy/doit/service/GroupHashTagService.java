package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.repository.*;
import com.ssafy.doit.service.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupHashTagService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final HashTagRepository hashTagRepository;
    @Autowired
    private final GroupHashTagRepository groupHashTagRepository;


    // 그룹 생성
    @Transactional
    //public void save(HttpServletRequest userReq, Group groupReq,  List<String> hashtags){
    public void save(Group groupReq, List<String> hashtags) {
//      Map<String,Object> userMap = (Map<String, Object>) jwtUtil.getUser(userReq.getHeader("accessToken"));
//      System.out.println(userMap);
//      User user = userRepository.findByEmail((String) userMap.get("email")).get();

        // 그룹에 대한 정보 저장
        Group group = groupRepository.save(Group.builder()
                .name(groupReq.getName())
                .content(groupReq.getContent())
                .maxNum(groupReq.getMaxNum())
                .startDate(LocalDate.now())
                .endDate(groupReq.getEndDate())
                .leader(1L)
                .build());

        // 입력한 해시태그들이
        for(String name : hashtags){
            Optional<HashTag> hashtag = hashTagRepository.findByName(name);
            HashTag tag;
            if(hashtag.isPresent()){ // 기존에 있으면 cnt +1
                tag = hashtag.get();
                tag.setCnt(tag.getCnt() + 1);
            }else{                   // 기존에 없으면 새로 생성
                tag = hashTagRepository.save(HashTag.builder()
                        .name(name).cnt(1).build());
            }

            // 그룹과 해시태그 연결 테이블에도 PK 값으로 저장
            //groupHashTagRepository.save(new GroupHashTag(group, tag));
            groupHashTagRepository.save(GroupHashTag.builder()
                    .group(group).hashTag(tag).build());
        }
    }

    // 특정 해시태그 포함한 그룹 찾기
    @Transactional
    public List<Group> findAllByHashTag(String hashtag){
        return groupRepository.findAllByHashTag(hashtag);
    }
}

