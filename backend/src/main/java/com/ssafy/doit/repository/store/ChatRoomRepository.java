package com.ssafy.doit.repository.store;

import com.ssafy.doit.model.store.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByChatRoomJoins_User_Id(Long uid);
}
