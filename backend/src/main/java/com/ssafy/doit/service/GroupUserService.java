package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    // 그룹 가입하기
    @Transactional
    public int join(Long userPk, Long groupPk) {
        User user = userRepository.findById(userPk).get();
        Group group = groupRepository.findById(groupPk).get();

        Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group, user);
        if(!opt.isPresent()){
            if(group.getTotalNum() == group.getMaxNum()) return 2;
            groupUserRepository.save(GroupUser.builder()
                    .group(group).user(user).build());
            group.setTotalNum(group.getTotalNum() + 1);
        }else return 1;
        return 0;
    }

    // 가입한 그룹 가져오기
    @Transactional
    public List<ResGroupList> findGroupByUserPk(Long userPk){
        User user = userRepository.findById(userPk).get();
        List<ResGroupList> list = new ArrayList<>();
        for(GroupUser group : user.groupList){
            list.add(new ResGroupList(group.getGroup()));
        }
        return list;
    }
}