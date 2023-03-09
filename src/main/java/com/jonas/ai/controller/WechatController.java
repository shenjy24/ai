package com.jonas.ai.controller;

import com.jonas.ai.bean.req.PublicAccountReq;
import com.jonas.ai.config.DirectReturn;
import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.SystemCode;
import com.jonas.ai.service.WechatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat/public/account/callback")
public class WechatController {

    private final WechatService wechatService;

    /**
     * 微信公众号回调接口
     *
     * @param req
     * @return
     */
    @DirectReturn
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String callback(PublicAccountReq req) {
        if (wechatService.checkPublicAccountSignature(req)) {
            return req.getEchostr();
        }
        return "";
    }

    @DirectReturn
    @PostMapping(produces = "application/xml;charset=utf-8")
    public String receiveFromWechat(@RequestBody String requestBody,
                          String signature,
                          String timestamp,
                          String nonce,
                          String openid,
                          String encrypt_type, String msg_signature) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encrypt_type, msg_signature, timestamp, nonce, requestBody);
        if (!wechatService.checkPublicAccountSignature(timestamp, nonce, signature)) {
            log.error("验签失败");
            throw new BizException(SystemCode.BIZ_ERROR);
        }
        return wechatService.receiveFromWechat(openid, requestBody, encrypt_type, msg_signature);
    }
}
