package com.jonas.ai.controller;

import com.jonas.ai.service.ChatService;
import com.jonas.ai.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @RequestMapping("/chat")
    public String chat(String unionId, String openId, String question) {
        return chatService.chat(unionId, openId, question);
    }
}
