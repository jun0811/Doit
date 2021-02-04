package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
