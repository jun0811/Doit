package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Group;
import com.ssafy.doit.model.GroupHashTag;
import com.ssafy.doit.model.GroupUser;
import com.ssafy.doit.model.user.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseGroup {
    private Long groupPk;
    private String name;
    private String content;
    private String category;
    private Long leader;
    private String startDate;
    private String endDate;
    private int score;
    private int totalNum;
    private int maxNum;

    private List<String> tags;
    private List<ResponseUser> users;

    public ResponseGroup(Group group){
        this.groupPk = group.getGroupPk();
        this.name = group.getName();
        this.content = group.getContent();
        this.category = group.getCategory();
        this.startDate = group.getStartDate().toString();
        this.endDate = group.getEndDate().toString();
        this.leader = group.getLeader();
        this.score = group.getScore();
        this.totalNum = group.getTotalNum();
        this.maxNum = group.getMaxNum();
        this.tags = this.getTags(group);
        this.users = this.getUsers(group);
    }

    public List<String> getTags(Group group){
        List<String> tag = new ArrayList<>();
        for(GroupHashTag hashTag : group.tagList){
            tag.add(hashTag.getHashTag().getName());
        }
        return tag;
    }

    public List<ResponseUser> getUsers(Group group){
        List<ResponseUser> user = new ArrayList<>();
        for(GroupUser gu : group.userList){
            user.add(new ResponseUser(gu.getUser()));
        }
        return user;
    }
}
