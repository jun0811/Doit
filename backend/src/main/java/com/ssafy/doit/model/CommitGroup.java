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

    private Long groupPk;
    @Column(name="`group_total`")
    private int total;
    private LocalDate date;
    private int cnt;

    @Builder
    public CommitGroup(LocalDate date, Long groupPk, int total, int cnt){
        this.date = date;
        this.groupPk = groupPk;
        this.total = total;
        this.cnt = cnt;
    }
}
