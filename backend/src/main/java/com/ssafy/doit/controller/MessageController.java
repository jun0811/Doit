package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.repository.chat.ChatMessageRepository;
import com.ssafy.doit.repository.chat.ChatRoomRepository;
import com.ssafy.doit.service.UserService;
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
    @Autowired
    private final UserService userService;

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void message(@Payload Map<String, Object> msg){
        ChatMessage message = new ChatMessage();
        message.setMessage((String) msg.get("message"));

        Number rid = (Integer) msg.get("roomPk");
        message.setRoomPk(rid.longValue());
        message.setUserPk(userService.currentUser());

        chatMessageRepository.saveAndFlush(message);
        template.convertAndSend("/subscribe/chat/room" + message.getRoomPk(), chatMessageRepository.getById(message.getId()));
    }
}
