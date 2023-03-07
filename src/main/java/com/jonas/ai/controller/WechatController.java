package com.jonas.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat")
public class WechatController {
    /**
     * 微信公众号回调接口
     *
     * @param xml
     * @return
     */
    @RequestMapping("/public/account/callback")
    public String callback(String xml) {
        log.info(xml);
        return xml;
    }
}
