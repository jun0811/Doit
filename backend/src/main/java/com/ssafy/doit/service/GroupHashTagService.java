package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.request.RequestGroup;
import com.ssafy.doit.model.response.ResGroupDetail;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupHashTagService {
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final HashTagRepository hashTagRepository;
    @Autowired
    private final GroupHashTagRepository groupHashTagRepository;


    // 특정 해시태그 포함한 그룹 찾기
    @Transactional
    public List<ResponseGroup> findAllByHashTag(String hashtag){
        List<Group> groupList = groupRepository.findAllByHashTagAndStatus(hashtag, "true");
        List<ResponseGroup> resList = new ArrayList<>();
        for(Group group : groupList){
            resList.add(new ResponseGroup(group));
        }
        return resList;
    }

    // 카테고리에 따른 그룹 분류 리스트
    @Transactional
    public List<ResponseGroup> findAllByCategory(String category){
        List<Group> groupList = groupRepository.findAllByCategoryAndStatus(category, "true");
        List<ResponseGroup> resList = new ArrayList<>();
        for(Group group : groupList){
            resList.add(new ResponseGroup(group));
        }
        return resList;
    }

    // 선택한 그룹 정보 제공
    @Transactional
    public ResGroupDetail findByGroupPk(Long groupPk) {
        Group group = groupRepository.findById(groupPk).get();
        return (new ResGroupDetail(group));
    }

    // 그룹 생성
    @Transactional
    public Long create(Long userPk, RequestGroup groupReq){
        // 그룹에 대한 정보 저장
        Group group = groupRepository.save(Group.builder()
                .name(groupReq.getName())
                .content(groupReq.getContent())
                .category(groupReq.getCategory())
                .maxNum(groupReq.getMaxNum())
                .startDate(LocalDate.now())
                .endDate(groupReq.getEndDate())
                .leader(userPk)
                .build());

        List<String> hashtags = groupReq.getHashtags();
        Long groupPk = group.getGroupPk();
        for(String hashtag : hashtags){
            findOrCreateHashTag(group, hashtag);
        }
        return groupPk;
    }

    // 그룹 정보 수정
    @Transactional
    public void updateGroup(Long userPk, Group groupReq) throws Exception {
        Optional<Group> group = groupRepository.findById(groupReq.getGroupPk());

        if(userPk == group.get().getLeader()){
            group.ifPresent(selectGroup ->{
                selectGroup.setName(groupReq.getName());
                selectGroup.setContent(groupReq.getContent());
                selectGroup.setCategory(groupReq.getCategory());
                selectGroup.setMaxNum(groupReq.getMaxNum());
                selectGroup.setEndDate(groupReq.getEndDate());
                selectGroup.setLeader(groupReq.getLeader());
                groupRepository.save(selectGroup);
            });
        }else throw new Exception("그룹장이 아닙니다."); // 로그인한 유저가 그룹장이 아니면 수정불가
    }

    // 그룹 해시태그 추가
    @Transactional
    public void updateHashTag(Long userPk, Long groupPk, String tag) throws Exception {
        Optional<Group> optGroup = groupRepository.findById(groupPk);
        if(userPk == optGroup.get().getLeader()) {
            Optional<HashTag> hashTag =  hashTagRepository.findByName(tag);
            if(hashTag.isPresent()){
                Optional<GroupHashTag> gh = groupHashTagRepository.findByGroupAndHashTag(optGroup.get(),hashTag.get());
                if(gh.isPresent()) throw new Exception("해시태그가 이미 존재합니다."); // 이미 해시태그 존재
            }
            findOrCreateHashTag(optGroup.get(), tag);
        } else throw new Exception("그룹장이 아닙니다."); // 로그인한 유저가 그룹장이 아니면 수정불가
    }

    // 그룹 해시태그 삭제
    @Transactional
    public void deleteHashTag(Long userPk, Long groupPk, String hashtag) throws Exception {
        Optional<Group> optGroup = groupRepository.findById(groupPk);
        if(userPk == optGroup.get().getLeader()) {
            HashTag hashTag =  hashTagRepository.findByName(hashtag).get();
            GroupHashTag gh = groupHashTagRepository.findByGroupAndHashTag(optGroup.get(),hashTag).get();
            groupHashTagRepository.delete(gh);
            hashTag.setCnt(hashTag.getCnt() - 1);
            hashTagRepository.save(hashTag);
        }else throw new Exception("그룹장이 아닙니다."); // 로그인한 유저가 그룹장이 아니면 수정불가
    }

    // 해시태그 있으면 추가, 없으면 cnt +1
    @Transactional
    public void findOrCreateHashTag(Group group, String hashtag){
        // 입력한 해시태그들이
        Optional<HashTag> opt = hashTagRepository.findByName(hashtag);
        HashTag tag;
        if(opt.isPresent()){        // 기존에 있으면 cnt +1
            tag = opt.get();
            tag.setCnt(tag.getCnt() + 1);
        }else {                     // 기존에 없으면 새로 생성
            tag = hashTagRepository.save(HashTag.builder()
                    .name(hashtag).cnt(1).build());
        }
        // 그룹과 해시태그 연결 테이블에도 PK 값으로 저장
        groupHashTagRepository.save(GroupHashTag.builder()
                .group(group).hashTag(tag).build());
    }
}