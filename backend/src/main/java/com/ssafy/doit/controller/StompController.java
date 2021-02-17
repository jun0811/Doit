package com.ssafy.doit.controller;

import com.ssafy.doit.model.group.Group;
import com.ssafy.doit.model.group.GroupUser;
import com.ssafy.doit.model.response.ResMyFeed;
import com.ssafy.doit.model.store.ChatMessage;
import com.ssafy.doit.model.notification.NotiType;
import com.ssafy.doit.model.notification.Notification;
import com.ssafy.doit.repository.group.GroupRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.service.NotiService;
import com.ssafy.doit.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class StompController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    @Autowired
    private final NotiService notiService;
    @Autowired
    private final GroupRepository groupRepository;

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

    @Transactional
    @MessageMapping("/noti")
    public void notify(Notification notification, @Header("accessToken") String Token){
        Long currentUser = userRepository.findByEmail(jwtUtil.getUser(Token)).get().getId();
        notification = notiService.setTarget(notification);

        if(notification.getNotiType() == NotiType.NEWFEED) {
            ResMyFeed feed = (ResMyFeed)notification.getTarget();
            Group group = groupRepository.findById(feed.getGroupPk()).get();

            for(GroupUser g : group.getUserList()){
                Long user = g.getUser().getId();
                if(user == currentUser) continue;

                Notification noti = new Notification();
                noti.setNotiType(notification.getNotiType());
                noti.setUserPk(user);
                noti.setTargetId(notification.getTargetId());
                noti.setTarget(group);
                notiService.save(noti);
                template.convertAndSend("/subscribe/noti/user/" + noti.getUserPk(), "reload");
            }
        }
        else {
            if(notification.getNotiType() == NotiType.NEWCHAT) notiService.refreshChat(notification);
            notiService.save(notification);
            template.convertAndSend("/subscribe/noti/user/" + notification.getUserPk(), notification);
        }
    }
}
