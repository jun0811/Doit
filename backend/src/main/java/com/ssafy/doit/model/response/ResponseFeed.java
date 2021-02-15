package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.FeedUser;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class ResponseFeed {
    private Long feedPk;

    private String media;
    private String content;
    private Long userPk;
    private String writer;
    private String feedType;

    private String authCheck;
    private String createDate;
    private String updateDate;

    private List<ResponseUser> authUsers;

    public ResponseFeed(Feed feed, String nickname){
        this.feedPk = feed.getFeedPk();
        this.writer = nickname;
        this.userPk = feed.getWriter();
        this.content = feed.getContent();
        this.feedType = feed.getFeedType();
        this.authCheck = feed.getAuthCheck();
        this.createDate = feed.getCreateDate().toString();
        this.updateDate = feed.getUpdateDate();
        this.media = feed.getMedia();
        this.authUsers = this.getAuthUsers(feed);
    }
    public List<ResponseUser> getAuthUsers(Feed feed){
        List<ResponseUser> user = new ArrayList<>();
        for(FeedUser fu : feed.userList){
            user.add(new ResponseUser(fu.getUser()));
        }
        return user;
    }
}
