package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.repository.chat.ChatMessageRepository;
import com.ssafy.doit.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MessageController {

    @Autowired
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void message(@Payload Map<String, Object> msg){
        ChatMessage message = new ChatMessage();
        message.setMessage((String) msg.get("message"));

        Number uid = (Integer) msg.get("userPk");
        message.setUserPk(uid.longValue());

        Number rid = (Integer) msg.get("roomPk");
        message.setRoomPk(rid.longValue());

        chatMessageRepository.saveAndFlush(message);
        template.convertAndSend("/subscribe/chat/room" + message.getRoomPk(), chatMessageRepository.findById(message.getId()));
    }
}
