package com.jonas.ai.bean.model;

import lombok.Data;

@Data
public class AccessToken {
    private String token;
    private int expiredTime;
}
