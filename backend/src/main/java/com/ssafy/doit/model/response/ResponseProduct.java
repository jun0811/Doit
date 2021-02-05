package com.ssafy.doit.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResponseProduct {
    Long getId();
    String getCategory();
    String getTitle();
    String getContent();
    String getImage();

    default Long getUserpk(){ return getUserId(); }

    default String getNickname(){
        return getUserNickname();
    }

    default String getEmail(){
        return getUserEmail();
    }

    @JsonIgnore
    Long getUserId();

    @JsonIgnore
    String getUserNickname();

    @JsonIgnore
    String getUserEmail();
}
