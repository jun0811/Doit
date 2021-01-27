package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupUserService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public int join(HttpServletRequest userReq, Long groupPk) {
        Map<String,Object> userMap = (Map<String, Object>) jwtUtil.getUser(userReq.getHeader("accessToken"));
        User user = userRepository.findByEmail((String) userMap.get("email")).get();
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
    public List<Group> findAllByUserPk(HttpServletRequest userReq){
        Map<String,Object> userMap = (Map<String, Object>) jwtUtil.getUser(userReq.getHeader("accessToken"));
        User user = userRepository.findByEmail((String) userMap.get("email")).get();
        List<Group> list = new ArrayList<>();
        for(GroupUser group : user.groupList){
            list.add(group.getGroup());
        }
        return list;
    }
}