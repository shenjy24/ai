package com.jonas.ai.controller;

import com.jonas.ai.bean.req.PublicAccountReq;
import com.jonas.ai.config.DirectReturn;
import com.jonas.ai.service.WechatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat")
public class WechatController {

    private final WechatService wechatService;

    /**
     * 微信公众号回调接口
     *
     * @param req
     * @return
     */
    @DirectReturn
    @RequestMapping("/public/account/callback")
    public String callback(PublicAccountReq req) {
        if (wechatService.checkPublicAccountSignature(req)) {
            return req.getEchostr();
        }
        return "";
    }
}
