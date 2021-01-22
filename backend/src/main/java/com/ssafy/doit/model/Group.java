package com.ssafy.doit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_pk")
    private Long id;

    @JsonIgnore
    private String name;    // 그룹 명
    private String content; // 그룹 소개글
    private int max;    // 최대 인원
    private int leader; // 팀장

    @ColumnDefault("0")
    private int score;  // 그룹 점수

    @ColumnDefault("1")
    private int total;  // 총 인원

    @Builder
    public Group(String name, String content, int max, int leader ){
        this.name = name;
        this.content = content;
        this.max = max;
        this.leader = leader;
    }
}
