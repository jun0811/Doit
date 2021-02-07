package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Comment;

import java.time.LocalDateTime;

public class ResponseComment {
    private Long commentPk;

    private String content;

    private Long feedPk;
    private Long userPk;

    private LocalDateTime createDate;
    private String updateDate;


}
