package com.ssafy.doit.model.response;

import lombok.Getter;

@Getter
public class ResRanking {
    private int ranking;
    private Long groupPk;
    private String groupName;

    public ResRanking(int ranking, Long groupPk, String name){
        this.ranking = ranking;
        this.groupPk = groupPk;
        this.groupName = name;
    }
}
