package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.chat.ChatMessage;
import com.ssafy.doit.model.response.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    ResponseMessage getById(Long id);
    List<ResponseMessage> findAllByRoomPk(Long roomPk);
}
