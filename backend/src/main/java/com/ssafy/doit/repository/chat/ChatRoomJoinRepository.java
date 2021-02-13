package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.chat.ChatRoomJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Long> {
    Optional<ChatRoomJoin> findChatRoomJoinByUser_IdAndChatRoom_Product_Id(Long uid, Long pid);
    Optional<ChatRoomJoin> findChatRoomJoinByUser_IdAndChatRoom_Id(Long uid, Long rid);
}
