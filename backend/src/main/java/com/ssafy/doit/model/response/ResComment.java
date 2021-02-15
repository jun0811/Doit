package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Comment;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class ResComment {

    private Long commentPk;

    private String content;
    private String image;
    private Long feedPk;
    private Long userPk;

    private LocalDateTime createDate;
    private String updateDate;

    public ResComment(Comment comment, Long userPk,String image){
        this.commentPk = comment.getCommentPk();
        this.content = comment.getContent();
        this.feedPk = comment.getFeedPk();
        this.userPk = userPk;
        this.image = image;
        this.createDate = comment.getCreateDate();

        this.updateDate = comment.getUpdateDate();
    }
}
