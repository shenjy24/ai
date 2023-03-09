package com.jonas.ai.service;

import com.jonas.ai.bean.req.PublicAccountReq;
import com.jonas.ai.config.PublicAccountConfig;
import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.ErrorCode;
import com.jonas.ai.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class WechatService {

    private final PublicAccountConfig publicAccountConfig;

    private final ChatService chatService;

    /**
     * 校验公众号回调接口的签名
     */
    public boolean checkPublicAccountSignature(PublicAccountReq req) {
        return checkPublicAccountSignature(req.getTimestamp(), req.getNonce(), req.getSignature());
    }

    public boolean checkPublicAccountSignature(String timestamp, String nonce, String signature) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{publicAccountConfig.getToken(), timestamp, nonce};
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            String tmpStr = StringUtil.byteToStr(digest);
            return signature.equalsIgnoreCase(tmpStr);
        } catch (NoSuchAlgorithmException e) {
            log.error("checkPublicAccountSignature error", e);
            throw new BizException(ErrorCode.REQUEST_ERROR);
        }
    }

    public String receiveFromWechat(String openid, String requestBody, String encType, String msgSignature) {
        String out = "";
        if (null == encType) {
            // 明文传输
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (null == outMessage) {
                return "";
            }
            out = outMessage.toXml();
        }
        log.info("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage wxMessage) {
        try {

            String content = chatService.chat(wxMessage.getFromUser(), wxMessage.getContent());

            return build(content, wxMessage);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        return null;
    }

    private WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage) {
        return WxMpXmlOutMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
