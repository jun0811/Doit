package com.ssafy.doit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupPk;

    @JsonIgnore
    private String name;    // 그룹 명
    private String content; // 그룹 소개글
    private Integer score;  // 그룹 점수
    private Integer total;  // 총 인원
    private Integer max;    // 최대 인원
    private Integer leader; // 팀장
}
