package com.jonas.ai.bean.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgReq {
    private String touser;
    private String msgtype;
    private Text text;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text {
        private String content;
    }
}
