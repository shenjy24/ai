package com.jonas.ai.config.model;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description 业务错误码
 */
public enum ErrorCode implements CodeStatus {
    SIGN_ERROR("signature error"),
    EXPRESS_ERROR("express error"),
    REQUEST_ERROR("请求异常"),
    CHAT_ERROR("聊天系统异常，请稍后再试！"),
    ;

    private final String code;
    private final String message;

    ErrorCode(String message) {
        this.code = SystemCode.BIZ_ERROR.getCode();
        this.message = message;
    }

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
