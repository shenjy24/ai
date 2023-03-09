package com.jonas.ai.controller;

import com.jonas.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @RequestMapping("/chat")
    public String chat(String openId, String question) {
        return chatService.chat(openId, question);
    }
}
