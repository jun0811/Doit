package com.ssafy.doit.model.response;

import com.ssafy.doit.model.store.Product;
import com.ssafy.doit.model.store.ChatRoom;
import com.ssafy.doit.model.store.ChatRoomJoin;
import lombok.Getter;

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
