package com.ssafy.doit.model.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class ResponseGroup {
    private Long groupPk;
    private String name;
    private String content;
    private int score;
    private int total;
    private int max;
    private int leader;
    private Date finishdate;

}
