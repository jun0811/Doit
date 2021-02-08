package com.ssafy.doit.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Message")
public class MessageTest {
    @Id
    private String id;
    private String message;
    private Long roomPk;
    private Long userPk;
}
