package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.chat.ChatRoomJoin;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.repository.chat.ChatMessageRepository;
import com.ssafy.doit.service.ChatService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ChatService chatService;
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    @ApiOperation(value = "채팅방 생성")
    @PostMapping("/createRoom")
    public Object CreateRoom(@RequestParam("product_pk") Long pid){
        ResponseBasic result = null;
        try{
            Long uid = userService.currentUser();
            ChatRoom resRoom = chatService.createRoom(uid, pid);
            result = new ResponseBasic(true, "success", resRoom);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "현재 로그인한 유저의 채팅방 리스트")
    @GetMapping("/getList")
    public Object getList(){
        ResponseBasic result = null;
        try{
            Long uid = userService.currentUser();
            List<ChatRoom> chatRooms = chatService.getList(uid);
            result = new ResponseBasic(true, "success", chatRooms);
        }catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "채팅방 입장")
    @GetMapping("/room/{roomPk}")
    public Object joinRoom(@PathVariable Long roomPk){
        ResponseBasic result = null;
        try{
            Long uid = userService.currentUser();
            Optional<ChatRoomJoin> opt = chatService.checkByRoom(uid, roomPk);

            if(!opt.isPresent()) throw new Exception("유저 불일치");

            Map<String, Object> res = new HashMap<>();
            res.put("room", chatService.getRoom(roomPk));
            res.put("messages", chatMessageRepository.findAllByRoomPk(roomPk));
            System.out.println(chatMessageRepository.findAllByRoomPk(roomPk));
            result = new ResponseBasic(true, "success", res);
        } catch (Exception e){
            e.printStackTrace();
            result = new ResponseBasic(false, e.getMessage(), null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
