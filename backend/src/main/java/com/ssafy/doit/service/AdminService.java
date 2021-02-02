package com.ssafy.doit.service;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.HashTag;
import com.ssafy.doit.repository.GroupHashTagRepository;
import com.ssafy.doit.repository.GroupRepository;
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

    public void deleteAllByGroup(Long groupPk) {
        Group group = groupRepository.findById(groupPk).get();
        List<GroupHashTag> list = groupHashTagRepository.findAllByGroup(group);
        for (GroupHashTag o: list) {
            groupHashTagRepository.delete(o);
        }

    }
}
