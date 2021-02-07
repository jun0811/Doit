package com.ssafy.doit.service;

import com.ssafy.doit.model.*;
import com.ssafy.doit.model.response.ResMyFeed;
import com.ssafy.doit.model.response.ResponseFeed;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    private final GroupUserRepository groupUserRepository;
    @Autowired
    private final FeedRepository feedRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FeedUserRepository feedUserRepository;
    @Autowired
    private final CommitUserRepository commitUserRepository;
    @Autowired
    private final CommitGroupRepository commitGroupRepository;

    // 그룹 내 피드 생성
    @Transactional
    public void createFeed(Long userPk, Feed feedReq) throws Exception {
        Group group = groupRepository.findById(feedReq.getGroupPk()).get();
        User user = userRepository.findById(userPk).get();

        Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group,user);
        if(!opt.isPresent())
            throw new Exception("해당 그룹에 가입되어 있지 않아 접근 불가");

        feedRepository.save(Feed.builder()
            //.media(feedReq.getMedia())
            .content(feedReq.getContent())
            .feedType(feedReq.getFeedType())
            .createDate(LocalDateTime.now())
            .groupPk(feedReq.getGroupPk())
            .writer(userPk).build());
    }

    // 그룹 내 피드 리스트
    @Transactional
    public List<ResponseFeed> groupFeedList(Long groupPk){
        List<Feed> list = feedRepository.findAllByGroupPkAndStatus(groupPk, "true");
        List<ResponseFeed> resList = new ArrayList<>();
        for(Feed feed : list){
            String nickname = userRepository.findById(feed.getWriter()).get().getNickname();
            resList.add(new ResponseFeed(feed, nickname));
        }
        return resList;
    }

    // 개인 피드 리스트
    @Transactional
    public List<ResMyFeed> userFeedList(Long userPk){
        List<Feed> list = feedRepository.findAllByWriterAndStatus(userPk, "true");
        List<ResMyFeed> resList = new ArrayList<>();
        for(Feed feed : list){
            String nickname = userRepository.findById(feed.getWriter()).get().getNickname();
            resList.add(new ResMyFeed(feed, nickname));
        }
        return resList;
    }

    // 개인 피드 수정
    @Transactional
    public void updateFeed(Long userPk, Feed feedReq) throws Exception {
        Optional<Feed> feed = feedRepository.findById(feedReq.getFeedPk());
        if(userPk == feed.get().getWriter()) {
            feed.ifPresent(selectFeed -> {
                selectFeed.setContent(feedReq.getContent());
                selectFeed.setFeedType(feedReq.getFeedType());
                //selectFeed.setMedia(feedReq.getMedia());
                selectFeed.setUpdateDate(LocalDateTime.now().toString());
                feedRepository.save(selectFeed);
            });
        }else throw new Exception("피드 작성자가 아닙니다.");
    }

    // 개인 피드 삭제
    @Transactional
    public void deleteFeed(Long userPk, Long feedPk) throws Exception {
        Optional<Feed> feed = feedRepository.findById(feedPk);
        if(userPk == feed.get().getWriter()) {
            feed.ifPresent(selectFeed -> {
                selectFeed.setStatus("false");
                feedRepository.save(selectFeed);
                //feedRepository.delete(selectFeed);
            });
        } else throw new Exception("피드 작성자가 아닙니다.");
    }

    // 인증피드 인증확인
    @Transactional
    public void authCheckFeed(Long userPk, Long feedPk) throws Exception {
        Feed feed = feedRepository.findById(feedPk).get();
        User user = userRepository.findById(userPk).get();
        Group group = groupRepository.findById(feed.getGroupPk()).get();

        Optional<GroupUser> optGU = groupUserRepository.findByGroupAndUser(group, user);
        if(!optGU.isPresent())
            throw new Exception("해당 그룹에 가입되어 있지 않아 접근 불가합니다.");

        if(userPk == feed.getWriter())
            throw new Exception("자신이 올린 피드에는 인증할 수 없습니다.");

        Optional<FeedUser> optFU = feedUserRepository.findByFeedAndUser(feed, user);
        if(optFU.isPresent())
            throw new Exception("이미 해당 피드에 인증을 하였습니다.");

        feed.setAuthCnt(feed.getAuthCnt() + 1);     // 인증피드 확인한 그룹원 수 +1
        feedUserRepository.save(FeedUser.builder()  // FeedUser 테이블에도
                .feed(feed).user(user).build());    // 그 피드에 인증 확인한 그룹원 추가

        Long groupPk = feed.getGroupPk();
        int cnt = feed.getAuthCnt();
        int total = groupRepository.findById(groupPk).get().getTotalNum();
        if (cnt >= Math.round(total * 0.7)) {       // 그룹의 현재 총 인원수의 70%(반올림) 이상이 인증확인하면
            feed.setAuthCheck("true");              // 그 인증피드는 인증완료
            feed.setAuthDate(LocalDateTime.now().toString());
            // 마일리지 점수 제공하기 // 인증완료되었다는 알림보내기
            Optional<CommitUser> optCU = commitUserRepository.findByUserPkAndDate(userPk, LocalDate.now());
            Optional<CommitGroup> optCG = commitGroupRepository.findByGroupPkAndDate(groupPk, LocalDate.now());
        }
    }
}