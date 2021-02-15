package com.ssafy.doit.service;

import com.ssafy.doit.model.feed.Feed;
import com.ssafy.doit.model.group.Group;
import com.ssafy.doit.repository.feed.FeedRepository;
import com.ssafy.doit.repository.group.GroupHashTagRepository;
import com.ssafy.doit.repository.group.GroupRepository;
import com.ssafy.doit.repository.group.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupHashTagRepository groupHashTagRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private FeedRepository feedRepository;

    // 관리자에 의한 그룹 삭제
    public void deleteGroupByAdmin(Long groupPk) {
        Optional<Group> groupInfo = groupRepository.findById(groupPk);
        if (groupInfo.isPresent()) {
            groupInfo.ifPresent(selectGroup -> {
                selectGroup.setStatus("false");
                groupRepository.save(selectGroup);
            });
        }
        groupHashTagRepository.deleteByGroupPk(groupPk);
        groupUserRepository.deleteByGroupPk(groupPk);
    }

    // 관리자에 의한 피드 삭제
    public void deleteFeed(Long feedPk) {
        Optional<Feed> feedInfo = feedRepository.findById(feedPk);
        if (feedInfo.isPresent()) {
            feedInfo.ifPresent(selectUser -> {
                selectUser.setStatus("false");
                feedRepository.save(selectUser);
            });
        }
    }
}
