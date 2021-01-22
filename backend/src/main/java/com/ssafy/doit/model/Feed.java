package com.ssafy.doit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPk;

    private Long groupPk;
    private String writer;
    private String media;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @CreatedDate
    private Date authDate;

    private String check;

    private String feedType;
}
