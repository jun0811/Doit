package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    // public void join(Authentication authentication, Group request)
    public int join(Long groupPk) {
        //User user = (User) authentication.getPrincipal();
        User user = userRepository.findByEmail("buhee1029@gmail.com").get(); // 테스트용
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
    // public void join(Authentication authentication, Group request)
    public List<Group> findAllByUserPk(Long userPk){
        //User user = (User) authentication.getPrincipal();
        Long id = userRepository.findByEmail("gksgpals96@naver.com").get().getId(); // 테스트용
        return groupRepository.findAllByUserPk(userPk);
    }
}
