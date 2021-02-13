package com.ssafy.doit.model.response;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.chat.ChatRoomJoin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseChatRoom {
    private Long id;
    private Product product;
    private ResponseUser otherUser;

    public ResponseChatRoom(ChatRoom chatRoom, Long currentUser){
        this.id = chatRoom.getId();
        this.product = chatRoom.getProduct();

        List<ChatRoomJoin> chatRoomJoins = chatRoom.getChatRoomJoins();
        this.otherUser = chatRoomJoins.get(0).getUser().getId() == currentUser ?
                new ResponseUser(chatRoomJoins.get(1).getUser()) : new ResponseUser(chatRoomJoins.get(0).getUser());
    }
}
