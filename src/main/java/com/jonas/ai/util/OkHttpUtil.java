package com.jonas.ai.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2019/02/19
 */
public class OkHttpUtil {

    private static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static Response synGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        Request request = new Request.Builder().url(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Response synGet(String url, Map<String, Object> params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (null != params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Response synPost(String url, Map<String, Object> params) {
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addEncoded(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Response synPost(String url, String json) {
        try {
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(url).post(requestBody).build();

            OkHttpClient okHttpClient = new OkHttpClient();
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
