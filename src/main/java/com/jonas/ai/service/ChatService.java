package com.jonas.ai.service;

import com.google.gson.JsonObject;
import com.jonas.ai.bean.constant.ChatRole;
import com.jonas.ai.bean.req.ChatReq;
import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.ErrorCode;
import com.jonas.ai.util.CacheUtil;
import com.jonas.ai.util.GsonUtil;
import com.jonas.ai.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChatService {

    @Value("${chatgpt.url}")
    private String chatUrl;
    @Value("${chatgpt.key}")
    private String chatToken;
    @Value("${chatgpt.model}")
    private String chatModel;

    public String chat(String unionId, String openId, String question) {
        try {
            Map<String, String> headers = new HashMap<>() {{
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + chatToken);
            }};

            ChatReq chatReq = buildChatReq(openId, question);
            HttpResponse<String> response = HttpUtil.post(chatUrl, GsonUtil.toJson(chatReq), headers);
            String responseBody = response.body();
            log.info("chat response={}, body={}", response, responseBody);
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new BizException(ErrorCode.CHAT_ERROR);
            }

            JsonObject jsonObject = GsonUtil.toJsonObject(responseBody);
            String answer = jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject()
                    .get("message").getAsJsonObject().get("content").getAsString();
            chatReq.getMessages().add(new ChatReq.ChatMessage(ChatRole.ASSISTANT, answer));
            CacheUtil.put(openId, chatReq.getMessages());
            return answer;
        } catch (Exception e) {
            log.error("chat error!", e);
            throw new BizException(ErrorCode.CHAT_ERROR);
        }
    }

    private ChatReq buildChatReq(String openId, String question) {
        ChatReq chatReq = new ChatReq();
        chatReq.setModel(chatModel);
        chatReq.setUser(openId);
        List<ChatReq.ChatMessage> messages = CacheUtil.get(openId);
        if (CollectionUtils.isEmpty(messages)) {
            messages.add(new ChatReq.ChatMessage(ChatRole.SYSTEM, "你是一个AI机器人助手"));
        }
        messages.add(new ChatReq.ChatMessage(ChatRole.USER, question));
        chatReq.setMessages(messages);
        return chatReq;
    }

}
