package com.ssafy.doit.controller;

import com.ssafy.doit.model.response.ResponseBasic;
import com.ssafy.doit.service.ChatService;
import com.ssafy.doit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
