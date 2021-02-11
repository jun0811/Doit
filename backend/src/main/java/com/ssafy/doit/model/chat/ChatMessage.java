package com.ssafy.doit.model.chat;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("ChatMessage")
public class ChatMessage {
    @Id
    private String id;
    private String message;

    @Indexed
    private Long roomPk;
    private Long userPk;
}
