package com.ssafy.doit.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate date;
    private Long userPk;
    private Long groupPk;

    @Builder
    public Commit(LocalDate date, Long userPk, Long groupPk){
        this.date = date;
        this.userPk = userPk;
        this.groupPk = groupPk;
    }
}
