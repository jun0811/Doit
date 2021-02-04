package com.ssafy.doit.service;

import com.ssafy.doit.model.Product;
import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.chat.ChatRoomJoin;
import com.ssafy.doit.model.user.User;
import com.ssafy.doit.repository.ProductRepository;
import com.ssafy.doit.repository.UserRepository;
import com.ssafy.doit.repository.chat.ChatRoomJoinRepository;
import com.ssafy.doit.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    @Autowired
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    @Autowired
    private final ProductRepository productRepository;

    public Long checkRoom(Long uid, Long pid) throws Exception {
        Optional<ChatRoomJoin> opt = chatRoomJoinRepository.findChatRoomJoinByUserAndProduct(uid, pid);
        if(opt.isPresent())
            return opt.get().getChatRoom().getId();

        return 0L;
    }


    public Long createRoom(Long uid, Long pid) throws Exception{
        Product product = productRepository.findById(pid).get();

        Long check = checkRoom(uid, pid);
        if(check > 0) return check;

        ChatRoom newRoom = new ChatRoom();
        newRoom.setProduct(product);
        chatRoomRepository.save(newRoom); // 채팅방 개설

        Long host = product.getUser().getId();
        createJoin(uid, newRoom);
        createJoin(host, newRoom);

        return newRoom.getId();
    }

    public void createJoin(Long id, ChatRoom room) throws Exception{
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin();
        chatRoomJoin.setUser(userRepository.findById(id).get());
        chatRoomJoin.setChatRoom(room);

        chatRoomJoinRepository.save(chatRoomJoin);
    }
}
