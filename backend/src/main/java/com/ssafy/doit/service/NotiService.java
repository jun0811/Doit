package com.ssafy.doit.service;

import com.ssafy.doit.model.notification.NotiType;
import com.ssafy.doit.model.notification.Notification;
import com.ssafy.doit.repository.GroupRepository;
import com.ssafy.doit.repository.NotiRepository;
import com.ssafy.doit.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiService {
    @Autowired
    private final NotiRepository notiRepository;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    @Autowired
    private final GroupRepository groupRepository;

    public List<Notification> getList(Long uid) throws Exception{
        List<Notification> notifications = notiRepository.findAllByUserPkAndStatusIsTrue(uid);
        for(Notification n : notifications){
            if(n.getNotiType() == NotiType.NEWCHAT)
                n.setTarget(chatRoomRepository.findById(n.getTargetId()));
            else n.setTarget(groupRepository.findById(n.getTargetId()));
        }

        return notifications;
    }

    public Notification setTarget(Notification notification) {
        if(notification.getNotiType() == NotiType.NEWCHAT)
            notification.setTarget(chatRoomRepository.findById(notification.getTargetId()));
        else notification.setTarget(groupRepository.findById(notification.getTargetId()));

        return notification;
    }

    public void setStatus(Long nid, Long uid) throws Exception{
        Notification notification = notiRepository.findById(nid).get();
        if(uid != notification.getUserPk()) throw new Exception("유저 불일치");

        notification.setStatus(false);
        notiRepository.save(notification);
    }

    public void save(Notification notification){
        notiRepository.save(notification);
    }
}
