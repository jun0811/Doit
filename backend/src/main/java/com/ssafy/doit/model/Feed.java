package com.ssafy.doit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPk;

    private Long groupPk;
    private String userPk;
    private String media;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    private int authCnt;

    @CreatedDate
    private Date authDate;

    private String authCheck;

    private String feedType;
    private String status;

}
