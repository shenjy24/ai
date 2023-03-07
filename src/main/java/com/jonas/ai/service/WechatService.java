package com.jonas.ai.service;

import com.jonas.ai.bean.req.PublicAccountReq;
import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.ErrorCode;
import com.jonas.ai.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Slf4j
@Service
public class WechatService {

    @Value("${wechat.public.account.token}")
    private String publicAccountToken;

    /**
     * 校验公众号回调接口的签名
     */
    public boolean checkPublicAccountSignature(PublicAccountReq req) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{publicAccountToken, req.getTimestamp(), req.getNonce()};
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
            return req.getSignature().equalsIgnoreCase(tmpStr);
        } catch (NoSuchAlgorithmException e) {
            log.error("checkPublicAccountSignature error", e);
            throw new BizException(ErrorCode.REQUEST_ERROR);
        }
    }
}
