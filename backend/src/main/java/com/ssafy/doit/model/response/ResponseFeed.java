package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Feed;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ResponseFeed {
    private Long feedPk;

    //private String media;
    private String content;
    private Long userPk;
    private String writer;
    private String feedType;

    private String authCheck;
    private String authDate;

    private String createDate;
    private String updateDate;

    public ResponseFeed(Feed feed, String nickname){
        this.feedPk = feed.getFeedPk();
        this.writer = nickname;
        this.userPk = feed.getWriter();
        this.content = feed.getContent();
        this.feedType = feed.getFeedType();
        this.authCheck = feed.getAuthCheck();
        this.authDate = feed.getAuthDate();
        this.createDate = feed.getCreateDate().toString();
        this.updateDate = feed.getUpdateDate();
    }
}
