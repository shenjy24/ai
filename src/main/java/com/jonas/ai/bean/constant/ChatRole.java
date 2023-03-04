package com.jonas.ai.bean.constant;

/**
 * ChatRole
 *
 * @author shenjy
 * @version 1.0
 * @date 2023-03-04
 */
public enum ChatRole {
    USER("user", "用户提问的问题"),
    ASSISTANT("assistant", "openai返回的答案"),
    SYSTEM("system", "指定聊天机器人的类型")
    ;
    private final String code;
    private final String message;

    ChatRole(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
