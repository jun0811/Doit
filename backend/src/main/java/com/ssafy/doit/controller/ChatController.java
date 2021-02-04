package com.ssafy.doit.controller;

import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.service.ChatService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ChatService chatService;

    @ApiOperation(value = "채팅방 생성")
    @PostMapping("/createRoom")
    public Object CreateRoom(@RequestParam("product_pk") Long pid){
        ResponseBasic result = null;
        try{
            Long uid = userService.currentUser();
            Long roomPk = chatService.createRoom(uid, pid);
            result = new ResponseBasic(true, "success", roomPk);
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
}
