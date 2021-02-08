package com.ssafy.doit.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResponseMessage {
    Long getId();
    String getMessage();

    default Long getRoom_Pk() { return getRoomPk(); }
    default Long getUser_Pk() { return getUserId(); }
    default String getNickName() { return getUserNickname(); }
    default String getEmail() { return getUserEmail(); }

    @JsonIgnore
    Long getRoomPk();

    @JsonIgnore
    Long getUserId();

    @JsonIgnore
    String getUserNickname();

    @JsonIgnore
    String getUserEmail();
}
