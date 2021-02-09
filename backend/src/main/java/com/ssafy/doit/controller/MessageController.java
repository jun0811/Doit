package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.repository.chat.ChatMessageRepository;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {

    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void message(ChatMessage chatMessage, @Header("accessToken") String Token){
        String currentUser = jwtUtil.getUser(Token);
        chatMessage.setUserPk(userRepository.findByEmail(currentUser).get().getId());
        chatMessageRepository.save(chatMessage);

        template.convertAndSend("/subscribe/chat/room/" + chatMessage.getRoomPk(), chatMessage);
    }
}
