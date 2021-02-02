package com.ssafy.doit.service;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.model.response.ResponseGroup;
import com.ssafy.doit.repository.FeedRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    @Autowired
    private final FeedRepository feedRepository;
    @Autowired
    private final UserRepository userRepository;

    // 그룹 내 피드 리스트
    @Transactional
    public List<ResponseFeed> getFeedList(Long groupPk){
        List<Feed> list = feedRepository.findAllByGroupPk(groupPk);
        List<ResponseFeed> resList = new ArrayList<>();
        for(Feed feed : list){
            String nickname = userRepository.findById(feed.getUserPk()).get().getNickname();
            resList.add(new ResponseFeed(feed, nickname));
        }
        return resList;
    }

    // 그룹 내 피드 생성
    @Transactional
    public void create(Long userPk, Feed feedReq){
        Feed feed = feedRepository.save(Feed.builder()
                .content(feedReq.getContent())
                .feedType(feedReq.getFeedType())
                .createDate(LocalDateTime.now())
                .groupPk(feedReq.getGroupPk())
                .userPk(userPk)
                .build());
    }
}
