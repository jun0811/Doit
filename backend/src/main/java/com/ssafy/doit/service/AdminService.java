package com.ssafy.doit.service;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.repository.FeedRepository;
import com.ssafy.doit.repository.GroupHashTagRepository;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
