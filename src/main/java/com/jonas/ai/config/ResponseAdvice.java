package com.jonas.ai.config;

import com.jonas.ai.config.model.JsonResult;
import com.jonas.ai.config.model.SystemCode;
import com.jonas.ai.util.GsonUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description 响应结果封装
 */
@ControllerAdvice
@ConditionalOnMissingClass
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (!(body instanceof JsonResult)) {
            JsonResult jsonResult = new JsonResult(SystemCode.SUCCESS, body);
            if (body instanceof String) {
                return GsonUtil.toJson(jsonResult);
            }
            return jsonResult;
        }
        return body;
    }
}
