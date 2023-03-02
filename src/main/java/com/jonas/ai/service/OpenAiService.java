package com.jonas.ai.service;

import com.jonas.ai.util.HttpUtil;
import com.jonas.ai.util.OkHttpUtil;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String AUTHORIZATION = "Bearer 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";

    public HttpResponse<String> chat(String question) {
        Map<String, String> headers = new HashMap<>() {{
           put("Content-Type", "application/json");
           put("Authorization", AUTHORIZATION);
        }};

        List<Map<String, String>> messages = new ArrayList<>() {{
           add(new HashMap<>() {{
               put("role", "user");
               put("content", question);
           }});
        }};
        Map<String, Object> args = new HashMap<>();
        args.put("model", "gpt-3.5-turbo");
        args.put("messages", messages);

        return HttpUtil.post(CHAT_URL, args, headers);
    }

    public Response chat2(String question) {
        List<Map<String, String>> messages = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("role", "user");
                put("content", question);
            }});
        }};
        Map<String, Object> args = new HashMap<>();
        args.put("model", "gpt-3.5-turbo");
        args.put("messages", messages);

        return OkHttpUtil.synPost(CHAT_URL, args);
    }
}
