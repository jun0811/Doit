package com.ssafy.doit.repository.chat;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.chat.ChatRoomJoin;
import com.ssafy.doit.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Long> {
    @Query("select j from ChatRoomJoin j where j.user.id = :uid and j.chatRoom.id = " +
            "(select r.id from ChatRoom r where r.product.id = :pid)")
    Optional<ChatRoomJoin> findChatRoomJoinByUserAndProduct(Long uid, Long pid);
}

//select * from chatroom_join
//where user_pk = (input userpk)
//    and room_pk = ( select room_pk from chatroom
//                    where product_pk = (input productPk)
//        )