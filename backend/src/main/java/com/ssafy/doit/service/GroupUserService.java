package com.ssafy.doit.service;

import com.ssafy.doit.model.*;
import com.ssafy.doit.model.response.ResGroupList;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.model.user.UserRole;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.GroupUserRepository;
import com.ssafy.doit.repository.MileageRepository;
import com.ssafy.doit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/***
 * @author : 김부희
 */
@Service
@RequiredArgsConstructor
public class GroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MileageRepository mileageRepository;

    // 가입 그룹 리스트
    @Transactional
    public List<ResGroupList> findGroupByUserPk(Long userPk){
        User user = userRepository.findById(userPk).get();
        List<ResGroupList> list = new ArrayList<>();
        for(GroupUser group : user.groupList){
            list.add(new ResGroupList(group.getGroup()));
        }
        return list;
    }

    // 그룹 가입하기
    @Transactional
    public void join(Long userPk, Long groupPk) throws Exception {
        User user = userRepository.findById(userPk).get();
        Optional<Group> optGroup = groupRepository.findByGroupPkAndStatus(groupPk, "true");
        if(!optGroup.isPresent()) throw new Exception("활동하지 않는 그룹입니다.");

        Group group = optGroup.get();
        Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group, user);
        if(!opt.isPresent()){
            if(group.getTotalNum() == group.getMaxNum()) throw new Exception("인원이 가득 찼습니다.");
            groupUserRepository.save(GroupUser.builder()
                    .group(group).user(user).build());

            group.setTotalNum(group.getTotalNum() + 1);
            groupRepository.save(group);

            user.setMileage(user.getMileage() + 100);
            userRepository.save(user);

            mileageRepository.save(Mileage.builder()
                    .content("그룹가입 축하 마일리지 지급")
                    .date(LocalDateTime.now())
                    .mileage("+100")
                    .user(user).build());
        }else throw new Exception("이미 가입된 그륩입니다.");
    }

    // 그룹 탈퇴하기
    @Transactional
    public void withdrawGroupUser(Long userPk, Long groupPk) throws Exception {
        User user = userRepository.findById(userPk).get();
        Group group = groupRepository.findById(groupPk).get();
        if(userPk == group.getLeader()) throw new Exception("그룹장은 탈퇴할 수 없습니다.");

        Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group, user);
        if(opt.isPresent()) {
            opt.ifPresent(selectGU -> groupUserRepository.delete(selectGU));

            group.setTotalNum(group.getTotalNum() - 1); //회원 수 감소
            groupRepository.save(group);

            user.setMileage(user.getMileage() - 1500);
            userRepository.save(user);

            mileageRepository.save(Mileage.builder()
                    .content("그룹탈퇴 마일리지 차감")
                    .date(LocalDateTime.now())
                    .mileage("-1,500")
                    .user(user).build());
        }else throw new Exception("가입되어 있지 않은 그룹원입니다.");
    }

    // 그룹 내 그룹원 강퇴시키기 (그룹장이)
    @Transactional
    public void kickOutGroupUser(Long userPk, Long groupPk, Long leader) throws Exception {
        Group group = groupRepository.findById(groupPk).get();
        if(leader == group.getLeader()){
            User user = userRepository.findById(userPk).get(); // 강퇴시킬 그룹원
            Optional<GroupUser> opt = groupUserRepository.findByGroupAndUser(group, user);
            opt.ifPresent(selectGU -> groupUserRepository.delete(selectGU));

            group.setTotalNum(group.getTotalNum() - 1); // 회원 수 감소
            groupRepository.save(group);

            user.setMileage(user.getMileage() - 1000);
            userRepository.save(user);

            mileageRepository.save(Mileage.builder()
                    .content("그룹강퇴 마일리지 차감")
                    .date(LocalDateTime.now())
                    .mileage("-1,000")
                    .user(user).build());
        }else throw new Exception("그룹장이 아닙니다.");
    }

    // 회원 탈퇴(강퇴), 가입된 모든 그룹에서 delete
    @Transactional
    public void deleteGroupByUser(Long userPk) throws Exception {
        User user = userRepository.findById(userPk).get();
        List<GroupUser> list = groupUserRepository.findByUser(user);

        for (GroupUser groupUser : list) {
            groupUserRepository.delete(groupUser);
            Group group = groupRepository.findById(groupUser.getGroup().getGroupPk()).get();
            if(group.getTotalNum() == 1){
                group.setStatus("false");
                groupRepository.save(group); continue;
            }
            Long newLeader = groupUserRepository.findTopByGroup(group).getUser().getId();
            group.setTotalNum(group.getTotalNum() - 1);     // 회원 수 감소
            if(userPk == group.getLeader()) {
                group.setLeader(newLeader);                 // 임의의 그룹원으로 그룹장 지정
            }
            groupRepository.save(group);
            // 새로운 그룹장에게 알림 보내기
        }
        user.setUserRole(UserRole.WITHDRAW);
        userRepository.save(user);
    }
}