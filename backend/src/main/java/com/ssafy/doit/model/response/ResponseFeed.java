package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Feed;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.util.Date;

@Getter
public class ResponseFeed {
    private Long feedPk;
    private Long groupPk;
    private String writer;
    private String media;
    private String content;
    private int authCnt;
    private Date authDate;
    private String check;
    private String feedType;

//    public ResponseFeed(Feed entity){
//        this.feedPk = entity.getFeedPk();
//        this.groupPk = entity.getGroupPk();
//        this.writer = entity.getUserPk();
//        this.content = entity.getContent();
//        this.authCnt = entity.getAuthCnt();
//        this.authDate = entity.getAuthDate();
//    }
}
