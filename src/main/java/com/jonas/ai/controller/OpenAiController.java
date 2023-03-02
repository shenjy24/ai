package com.jonas.ai.controller;

import com.jonas.ai.service.OpenAiService;
import com.jonas.ai.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openai")
public class OpenAiController {

    private final OpenAiService openAiService;

    @RequestMapping("/chat")
    public String chat(String question) {
        return GsonUtil.toJson(openAiService.chat(question));
    }

    @RequestMapping("/chat2")
    public String chat2(String question) {
        return GsonUtil.toJson(openAiService.chat2(question));
    }
}
