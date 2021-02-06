package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
