package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.chat.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomPk(Long roomPk);
}
