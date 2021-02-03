package com.ssafy.doit.service;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.FeedUser;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.FeedRepository;
import com.ssafy.doit.repository.FeedUserRepository;
import com.ssafy.doit.repository.GroupRepository;
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
    private final GroupRepository groupRepository;
    @Autowired
    private final FeedRepository feedRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FeedUserRepository feedUserRepository;

    // 그룹 내 피드 생성
    @Transactional
    public void createFeed(Long userPk, Feed feedReq){
        feedRepository.save(Feed.builder()
            //.media(feedReq.getMedia())
            .content(feedReq.getContent())
            .feedType(feedReq.getFeedType())
            .createDate(LocalDateTime.now())
            .groupPk(feedReq.getGroupPk())
            .writer(userPk)
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
        List<Feed> list = feedRepository.findAllByWriterAndStatus(userPk, "true");
        return getResponseFeed(list);
    }

    private List<ResponseFeed> getResponseFeed(List<Feed> list) {
        List<ResponseFeed> resList = new ArrayList<>();
        for(Feed feed : list){
            String nickname = userRepository.findById(feed.getWriter()).get().getNickname();
            resList.add(new ResponseFeed(feed, nickname));
        }
        return resList;
    }

    // 개인 피드 수정
    @Transactional
    public void updateFeed(Feed feedReq) {
        Optional<Feed> feed = feedRepository.findById(feedReq.getFeedPk());
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
        Optional<Feed> feed = feedRepository.findById(feedPk);
        feed.ifPresent(selectFeed ->{
            selectFeed.setStatus("false");
            feedRepository.save(selectFeed);
            //feedRepository.delete(selectFeed);
        });
    }

    // 인증피드 인증확인
    @Transactional
    public int authCheckFeed(Long userPk, Long feedPk) {
        Feed feed = feedRepository.findById(feedPk).get();
        User user = userRepository.findById(userPk).get();

        if(userPk == feed.getWriter()) return 1;    // 자신이 올린 인증피드에는 인증확인 못함

        feed.setAuthCnt(feed.getAuthCnt() + 1);     // 인증피드 확인한 그룹원 수 +1
        feedUserRepository.save(FeedUser.builder()  // FeedUser 테이블에도
                .feed(feed).user(user).build());    // 그 피드에 인증 확인한 그룹원 추가

        Long groupPk = feed.getGroupPk().longValue();
        int cnt = feed.getAuthCnt();
        int total = groupRepository.findById(groupPk).get().getTotalNum();

        if (cnt >= Math.round(total * 0.7)) {       // 그룹 총 인원수의 70%(반올림) 이상이 인증확인하면
            feed.setAuthCheck("true");              // 그 인증피드는 인증완료
            feed.setAuthDate(LocalDateTime.now().toString());
            // commit 테이블에 추가
        }
        return 0;
    }
}
