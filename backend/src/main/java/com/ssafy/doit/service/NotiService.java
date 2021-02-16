package com.ssafy.doit.service;

import com.ssafy.doit.model.notification.NotiType;
import com.ssafy.doit.model.notification.Notification;
import com.ssafy.doit.repository.feed.FeedRepository;
import com.ssafy.doit.repository.group.GroupRepository;
import com.ssafy.doit.repository.NotiRepository;
import com.ssafy.doit.repository.store.ChatRoomRepository;
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
    @Autowired
    private final FeedRepository feedRepository;

    public List<Notification> getList(Long uid) throws Exception{
        List<Notification> notifications = notiRepository.findAllByUserPkOrderByNotiDateDesc(uid);
        for(Notification n : notifications){
            if(n.getNotiType() == NotiType.NEWCHAT)
                n.setTarget(chatRoomRepository.findById(n.getTargetId()).get());
            else if(n.getNotiType() == NotiType.COMMENT) n.setTarget(feedRepository.findById(n.getTargetId()).get());
            else n.setTarget(groupRepository.findById(n.getTargetId()).get());
        }

        return notifications;
    }

    public Notification setTarget(Notification notification) {
        if(notification.getNotiType() == NotiType.NEWCHAT)
            notification.setTarget(chatRoomRepository.findById(notification.getTargetId()).get());
        else if(notification.getNotiType() == NotiType.COMMENT) notification.setTarget(feedRepository.findById(notification.getTargetId()).get());
        else notification.setTarget(groupRepository.findById(notification.getTargetId()).get());

        return notification;
    }

    public void save(Notification notification){
        notiRepository.save(notification);
    }
    public void delete(Long id) {notiRepository.delete(notiRepository.findById(id).get());}
}
