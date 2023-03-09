package com.jonas.ai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class PublicAccountConfig {

    @Value("${wechat.public.account.token}")
    private String token;

    @Value("${wechat.public.account.appId}")
    private String appId;

    @Value("${wechat.public.account.secret}")
    private String secret;

    @Value("${wechat.public.account.access_token_url}")
    private String accessTokenUrl;

    /**
     * 发送客服消息
     */
    @Value("${wechat.public.account.send_url}")
    private String sendUrl;
}
