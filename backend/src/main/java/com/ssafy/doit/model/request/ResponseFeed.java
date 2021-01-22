package com.ssafy.doit.model.request;

import com.ssafy.doit.model.Feed;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.util.Date;

@Getter
public class ResponseFeed {
    private Long feedPk;
    private String writer;
    private String media;
    private String content;
    private Date authDate;
    private String check;
    private String feedType;

    public ResponseFeed(Feed entity){
        this.feedPk = entity.getFeedPk();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
        this.authDate = entity.getAuthDate();
    }
}
