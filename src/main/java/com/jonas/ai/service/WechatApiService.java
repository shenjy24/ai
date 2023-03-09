package com.jonas.ai.service;

import com.google.gson.JsonObject;
import com.jonas.ai.bean.model.AccessToken;
import com.jonas.ai.bean.req.MsgReq;
import com.jonas.ai.config.PublicAccountConfig;
import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.ErrorCode;
import com.jonas.ai.util.GsonUtil;
import com.jonas.ai.util.HttpUtil;
import com.jonas.ai.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WechatApiService {

    private final ChatService chatService;

    private final PublicAccountConfig publicAccountConfig;

    private AccessToken accessToken = new AccessToken();

    /**
     * 给用户发送消息
     */
    @Async
    public void sendMsg(String openId, String question) {
        log.info("调用sendMsg, thread={}, openId={}, question={}", Thread.currentThread().getName(), openId, question);
        String content = chatService.chat(openId, question);
        MsgReq msgReq = new MsgReq(openId, "text", new MsgReq.Text(content));
        String msgUrl = publicAccountConfig.getSendUrl() + getAccessToken();
        HttpResponse<String> response = HttpUtil.post(msgUrl, GsonUtil.toJson(msgReq), new HashMap<>());

    }

    public String getAccessToken() {
        int now = TimeUtil.second();
        if (null != accessToken && accessToken.getExpiredTime() > now) {
            return accessToken.getToken();
        }

        Map<String, Object> args = new HashMap<>() {{
            put("grant_type", "client_credential");
            put("appid", publicAccountConfig.getAppId());
            put("secret", publicAccountConfig.getSecret());
        }};
        HttpResponse<String> response = HttpUtil.post(publicAccountConfig.getAccessTokenUrl(), GsonUtil.toJson(args), new HashMap<>());
        String responseBody = response.body();
        log.info("get access token response={}, body={}", response, responseBody);
        if (response.statusCode() != HttpStatus.OK.value()) {
            log.error("get access token error");
            throw new BizException(ErrorCode.REQUEST_ERROR);
        }
        JsonObject jsonObject = GsonUtil.toJsonObject(responseBody);
        String token = jsonObject.get("access_token").getAsString();
        int expiresIn = jsonObject.get("expires_in").getAsInt();
        accessToken.setToken(token);
        accessToken.setExpiredTime(now + expiresIn);
        return token;
    }
}
