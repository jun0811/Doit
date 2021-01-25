package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.repository.GroupHashTagRepository;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.HashTagRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //public void save(HttpServletRequest userReq, Group groupReq,  List<String> hashtags){
    public void save(Group groupReq, List<String> hashtags) {
//      Map<String, Object> userMap = (Map<String, Object>) jwtUtil.getUser(userReq.getHeader("accessToken"));
//      System.out.println(userMap);
//      User user = userRepository.findByEmail((String) userMap.get("email")).get();
        Group group = groupRepository.save(Group.builder()
                .name(groupReq.getName())
                .content(groupReq.getContent())
                .maxNum(groupReq.getMaxNum())
                .startDate(LocalDate.now())
                .endDate(groupReq.getEndDate())
                .leader(1L)
                .build());

        for(String name : hashtags){
            Optional<HashTag> hashtag = hashTagRepository.findByName(name);
            HashTag tag;
            if(hashtag.isPresent()){
                tag = hashtag.get();
                tag.setCnt(tag.getCnt() + 1);
            }else{
                tag = hashTagRepository.save(HashTag.builder()
                        .name(name).cnt(1).build());
            }
            //groupHashTagRepository.save(new GroupHashTag(group, tag));
            groupHashTagRepository.save(GroupHashTag.builder()
                    .group(group)
                    .hashTag(tag)
                    .build());
        }
    }
}

