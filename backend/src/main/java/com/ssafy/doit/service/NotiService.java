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
        for(Notification noti : notifications)
            noti = setTarget(noti);

        return notifications;
    }

    public Notification setTarget(Notification notification) {
        if(notification.getNotiType() == NotiType.NEWCHAT)
            notification.setTarget(chatRoomRepository.findById(notification.getTargetId()).get());
        else if(notification.getNotiType() == NotiType.COMMENT)
            notification.setTarget(feedRepository.findById(notification.getTargetId()).get());
        else notification.setTarget(groupRepository.findById(notification.getTargetId()).get());

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
