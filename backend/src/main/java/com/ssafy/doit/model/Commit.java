package com.ssafy.doit.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "`commit`")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commitPk;
    private String date;
    private Long userPk;
    private Long groupPk;

    @Builder
    public Commit(String date, Long userPk, Long groupPk){
        this.date = date;
        this.userPk = userPk;
        this.groupPk = groupPk;
    }
}
