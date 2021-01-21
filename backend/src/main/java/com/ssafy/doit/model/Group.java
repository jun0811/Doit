package com.ssafy.doit.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="Group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupPk;

    @Column(length = 255, nullable = false)
    private String name; // 그룹 명

    @Setter
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content; //그룹 소개글

    @Setter
    @ColumnDefault("0") //default 0
    private Integer score; // 그룹 점수

    @Setter
    //@ColumnDefault("1") //default 1 팀원
    private Integer total; // 총인원

    @Setter
    private Integer max; // 최대인원

    @Setter
    @Column(nullable = false)
    private Integer leader; // 팀장

}
