package com.jonas.ai.bean.req;

import lombok.Data;

@Data
public class PublicAccountReq {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
