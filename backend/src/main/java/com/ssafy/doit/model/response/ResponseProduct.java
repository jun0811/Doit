package com.ssafy.doit.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResponseProduct {
    Long getId();
    String getCategory();
    String getTitle();
    String getContent();
    String getImage();
    int getMileage();
    boolean getStatus();

    default Long getUser_pk(){ return getUserId(); }

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
