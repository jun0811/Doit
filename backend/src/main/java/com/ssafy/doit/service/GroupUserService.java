package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void join(Long userPk, Long groupPk) throws Exception {
        User user = userRepository.findById(userPk).get();
        Group group = groupRepository.findById(groupPk).get();

        Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group, user);
        if(!opt.isPresent()){
            if(group.getTotalNum() == group.getMaxNum()) throw new Exception("인원이 가득 찼습니다.");
            groupUserRepository.save(GroupUser.builder()
                    .group(group).user(user).build());
            group.setTotalNum(group.getTotalNum() + 1);
            groupRepository.save(group);
        }else throw new Exception("이미 가입된 그륩입니다.");
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

    // 그룹 내 그룹원 강퇴시키기
    public void deleteGroupUser(Long userPk, Long groupPk, Long leader) throws Exception {
        Group group = groupRepository.findById(groupPk).get();
        if(leader == group.getLeader()){
            User user = userRepository.findById(userPk).get(); // 강퇴시킬 그룹원
            Optional<GroupUser> groupUser = groupUserRepository.findByGroupAndUser(group,user);
            groupUser.ifPresent(selectUser ->{
               groupUserRepository.delete(selectUser);
            });
            group.setTotalNum(group.getTotalNum() - 1); //회원 수 감소
            groupRepository.save(group);
        }else throw new Exception("그룹장이 아닙니다.");
    }
}