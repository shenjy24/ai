package com.jonas.ai.util;

import com.jonas.ai.config.model.BizException;
import com.jonas.ai.config.model.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Slf4j
public class HttpUtil {

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * 发起post请求
     *
     * @param uri     路径
     * @param args    body参数
     * @param headers 头部参数
     * @return 返回响应
     */
    public static HttpResponse<String> post(String uri, Map<String, Object> args, Map<String, String> headers) {
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(GsonUtil.toJson(args));
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(10))
                .POST(body);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.header(header.getKey(), header.getValue());
        }
        HttpRequest request = builder.build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("post error!", e);
            throw new BizException(ErrorCode.REQUEST_ERROR);
        }
    }
}
