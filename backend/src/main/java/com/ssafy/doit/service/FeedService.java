package com.ssafy.doit.service;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.repository.FeedRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {

    @Autowired
    private final FeedRepository feedRepository;
    @Autowired
    private final UserRepository userRepository;

    // 그룹 내 피드 생성
    @Transactional
    public void createFeed(Long userPk, Feed feedReq){
        feedRepository.save(Feed.builder()
            //.media(feedReq.getMedia())
            .content(feedReq.getContent())
            .feedType(feedReq.getFeedType())
            .createDate(LocalDateTime.now())
            .groupPk(feedReq.getGroupPk())
            .userPk(userPk)
            .build());
    }

    // 그룹 내 피드 리스트
    @Transactional
    public List<ResponseFeed> groupFeedList(Long groupPk){
        List<Feed> list = feedRepository.findAllByGroupPkAndStatus(groupPk, "true");
        return getResponseFeed(list);
    }

    // 개인 피드 리스트
    @Transactional
    public List<ResponseFeed> userFeedList(Long userPk){
        List<Feed> list = feedRepository.findAllByUserPkAndStatus(userPk, "true");
        return getResponseFeed(list);
    }

    private List<ResponseFeed> getResponseFeed(List<Feed> list) {
        List<ResponseFeed> resList = new ArrayList<>();
        for(Feed feed : list){
            String nickname = userRepository.findById(feed.getUserPk()).get().getNickname();
            resList.add(new ResponseFeed(feed, nickname));
        }
        return resList;
    }

    // 개인 피드 수정
    @Transactional
    public void updateFeed(Feed feedReq) {
        Optional<Feed> feed = feedRepository.findByFeedPk(feedReq.getFeedPk());
        feed.ifPresent(selectFeed ->{
            selectFeed.setContent(feedReq.getContent());
            selectFeed.setFeedType(feedReq.getFeedType());
            //selectFeed.setMedia(feedReq.getMedia());
            selectFeed.setUpdateDate(LocalDateTime.now().toString());
            feedRepository.save(selectFeed);
        });
    }

    // 개인 피드 삭제
    @Transactional
    public void deleteFeed(Long feedPk) {
        Optional<Feed> feed = feedRepository.findByFeedPk(feedPk);
        feed.ifPresent(selectFeed ->{
            feedRepository.delete(selectFeed);
        });
    }
}
