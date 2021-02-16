package com.ssafy.doit.service;

import com.ssafy.doit.model.notification.NotiType;
import com.ssafy.doit.model.notification.Notification;
import com.ssafy.doit.model.response.ResponseChatRoom;
import com.ssafy.doit.model.store.ChatRoom;
import com.ssafy.doit.repository.feed.FeedRepository;
import com.ssafy.doit.repository.group.GroupRepository;
import com.ssafy.doit.repository.NotiRepository;
import com.ssafy.doit.repository.store.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotiService {
    @Autowired
    private final NotiRepository notiRepository;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final FeedRepository feedRepository;

    public List<Notification> getList(Long uid) throws Exception{
        List<Notification> notifications = notiRepository.findAllByUserPkOrderByNotiDateDesc(uid);
        List<Notification> res  = new ArrayList<>();

        for(Notification noti : notifications){
            setTarget(noti);
            if(noti.getTarget() == null)
                notiRepository.delete(noti);
            else res.add(noti);
        }

        return res;
    }

    public Notification setTarget(Notification notification) {
        Long id = notification.getTargetId();

        if(notification.getNotiType() == NotiType.NEWCHAT && chatRoomRepository.findById(id).isPresent()){
            ChatRoom chatRoom = chatRoomRepository.findById(id).get();
            notification.setTarget(new ResponseChatRoom(chatRoom, notification.getUserPk()));
        }
        else if(notification.getNotiType() == NotiType.COMMENT && feedRepository.findById(id).isPresent())
            notification.setTarget(feedRepository.findById(id).get());
        else if(groupRepository.findById(id).isPresent())
            notification.setTarget(groupRepository.findById(id).get());

        return notification;
    }

    public void refreshChat(Notification noti){
        Optional<Notification> notification = notiRepository.findByUserPkAndNotiTypeAndTargetId(noti.getUserPk(), NotiType.NEWCHAT, noti.getTargetId());
        if(notification.isPresent()) notiRepository.delete(notification.get());
    }

    public void save(Notification notification){
        notiRepository.save(notification);
    }
    public void delete(Long id) {notiRepository.delete(notiRepository.findById(id).get());}
}
