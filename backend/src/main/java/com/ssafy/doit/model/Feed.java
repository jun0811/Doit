package com.ssafy.doit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@NoArgsConstructor
public class Feed {
    //피드 model

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPk;

    private String writer;
    private String media;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @CreatedDate
    private Date authDate;

    private String check;

    private String feedType;
}
