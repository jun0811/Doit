package com.ssafy.doit.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "`commit_group`")
public class CommitGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Long groupPk;
    private int cnt;

    @Builder
    public CommitGroup(LocalDate date, Long groupPk, int cnt){
        this.date = date;
        this.groupPk = groupPk;
        this.cnt = cnt;
    }
}
