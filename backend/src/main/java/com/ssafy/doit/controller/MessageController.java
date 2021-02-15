package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class MessageController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final RedisTemplate<String, ChatMessage> redisTemplate;

    private final SimpMessagingTemplate template;
    private final String MESSAGE_KEY = "Room_idx:";

    @MessageMapping("/chat")
    public void message(ChatMessage chatMessage, @Header("accessToken") String Token){
        String currentUser = jwtUtil.getUser(Token);
        chatMessage.setUserPk(userRepository.findByEmail(currentUser).get().getId());
        chatMessage.setLocalDateTime(LocalDateTime.now());

        ListOperations<String, ChatMessage> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(MESSAGE_KEY + chatMessage.getRoomPk(), chatMessage);

        template.convertAndSend("/subscribe/chat/room/" + chatMessage.getRoomPk(), chatMessage);
    }
}
