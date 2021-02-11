package com.ssafy.doit.model.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestFeed {
    private Long feedPk;

    private String media;
    private String content;
    private String feedType; // 인증피드 : true, 자유피드 : false

    private int authCnt;
    private String authDate;
    private String authCheck;

    private LocalDateTime createDate;
    private String updateDate;

    private String status;
    private Long groupPk;
    private Long writer;

    public RequestFeed toEntity(){
        RequestFeed build = RequestFeed.builder()
                .feedPk(feedPk)
                .media(media)
                .content(content)
                .feedType(feedType)
                .createDate(createDate)
                .groupPk(groupPk)
                .writer(writer)
                .build();
        return build;
    }

    @Builder
    public RequestFeed(Long feedPk,String media,String content,String feedType, LocalDateTime createDate,Long groupPk, Long writer){
        this.feedPk = feedPk;
        this.media = media;
        this.content = content;
        this.feedType = feedType;
        this.groupPk = groupPk;
        this.writer = writer;
        this.createDate = createDate;

        this.updateDate = "0-0-0 0:0:0";
        this.authDate = "0-0-0 0:0:0";
        this.authCheck = "false";
        this.status = "true";
    }

}
