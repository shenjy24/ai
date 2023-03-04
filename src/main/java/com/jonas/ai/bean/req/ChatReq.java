package com.jonas.ai.bean.req;

import com.jonas.ai.bean.constant.ChatRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OpenAi聊天接口请求类
 *
 * @author shenjy
 * @version 1.0
 * @date 2023-03-04
 */
@Data
public class ChatReq {
    private String model;
    private List<ChatMessage> messages;

    private String user;

    public void addMessage(String role, String message) {
        messages.add(new ChatMessage(role, message));
    }

    @Data
    @NoArgsConstructor
    public static class ChatMessage {
        private String role;
        private String content;

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public ChatMessage(ChatRole role, String content) {
            this.role = role.getCode();
            this.content = content;
        }
    }
}
