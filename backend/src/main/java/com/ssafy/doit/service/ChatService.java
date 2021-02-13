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

    public ChatRoom checkByProduct(Long uid, Long pid) throws Exception {
        Optional<ChatRoomJoin> opt = chatRoomJoinRepository.findChatRoomJoinByUser_IdAndChatRoom_Product_Id(uid, pid);
        if(opt.isPresent())
            return opt.get().getChatRoom();

        return null;
    }

    public Optional<ChatRoomJoin> checkByRoom(Long uid, Long rid) throws Exception{
        return chatRoomJoinRepository.findChatRoomJoinByUser_IdAndChatRoom_Id(uid, rid);
    }

    public ChatRoom createRoom(Long uid, Long pid) throws Exception{
        Product product = productRepository.findById(pid).get();
        User currentUser = userRepository.findById(uid).get();
        if(product.getUser().equals(currentUser))
            throw new Exception("판매자와 동일 유저");

        ChatRoom resRoom = checkByProduct(uid, pid);
        if(resRoom != null) return resRoom;

        resRoom = new ChatRoom();
        resRoom.setProduct(product);
        chatRoomRepository.save(resRoom); // 채팅방 개설

        Long host = product.getUser().getId();
        createJoin(uid, resRoom);
        createJoin(host, resRoom);

        return resRoom;
    }

    public void createJoin(Long id, ChatRoom room) throws Exception{
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin();
        chatRoomJoin.setUser(userRepository.findById(id).get());
        chatRoomJoin.setChatRoom(room);

        chatRoomJoinRepository.save(chatRoomJoin);
    }

    public List<ChatRoom> getList(Long uid) throws Exception{
        return chatRoomRepository.findAllByUser(uid);
    }

    public ChatRoom getRoom(Long rid) throws Exception{
        return chatRoomRepository.findById(rid).get();
    }
}
