package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.repository.chat.ChatMessageRepository;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {

    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void message(@Payload ChatMessage chatMessage){
        chatMessageRepository.save(chatMessage);
        System.out.println(chatMessage);

        template.convertAndSend("/subscribe/chat/room" + chatMessage.getRoomPk(), chatMessage);
    }
}
