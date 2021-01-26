package com.ssafy.doit.model.response;

import com.ssafy.doit.model.user.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ResponseGroup {
    private Long groupPk;
    private String name;
    private int maxNum;
    private String content;
    private Long leader;
    private LocalDate startDate;
    private LocalDate endDate;
    private int score;
    private int totalNum;

//    private List<String> tagList;
//    private List<User> userList;
}
