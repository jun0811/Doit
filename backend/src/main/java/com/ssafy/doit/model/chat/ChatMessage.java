package com.ssafy.doit.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.doit.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_pk")
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "room_pk")
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;
}
