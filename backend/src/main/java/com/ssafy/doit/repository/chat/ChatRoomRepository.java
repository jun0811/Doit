package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByChatRoomJoins_User_Id(Long uid);
}
