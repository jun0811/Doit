package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select j.chatRoom from ChatRoomJoin j where j.user.id = :uid")
    List<ChatRoom> findAllByUser(Long uid);
}
