package com.ssafy.doit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "`group`")
public class Group{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupPk;

    private String name;
    private String content;
    private String image;
    private int score;
    private int totalNum;
    private int maxNum;
    private Long leader;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "group")
    public List<GroupHashTag> tagList; // 태그리스트

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "group")
    public List<GroupUser> userList; // 유저리스트

    @Builder
    public Group(String name, String content, String image, int maxNum,
                 Long leader, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.content = content;
        this.image = image;
        this.totalNum = 0;
        this.maxNum = maxNum;
        this.score = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leader = leader;
        this.status = "true";
    }
}
