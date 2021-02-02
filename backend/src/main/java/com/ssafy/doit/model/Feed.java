package com.ssafy.doit.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "`feed`")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long userPk; // writer

    @Builder
    public Feed(String content, String feedType, LocalDateTime createDate,
                Long groupPk, Long userPk){
        //this.media = media;
        this.content = content;
        this.feedType = feedType;
        this.groupPk = groupPk;
        this.userPk = userPk;
        this.createDate = createDate;

        this.updateDate = "0-0-0 0:0:0";
        this.authDate = "0-0-0 0:0:0";
        this.authCheck = "false";
        this.status = "true";
    }

}