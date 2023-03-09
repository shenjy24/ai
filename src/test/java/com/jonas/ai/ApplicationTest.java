package com.jonas.ai;

import com.google.gson.JsonObject;
import com.jonas.ai.service.ChatService;
import com.jonas.ai.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class ApplicationTest {

    @Autowired
    private ChatService chatService;

    @Test
    void testChat1() {
        String answer = chatService.chat("openId","pip常用指令");
        System.out.println(answer);
    }

    @Test
    void test() {
        HttpClient client = HttpClient.newBuilder().build();

        String args = """
                {
                	"model": "gpt-3.5-turbo",
                	"messages": [
                  		{"role": "system", "content": "你是中英文翻译机器人。"},
                        {"role": "user", "content": "今年是公元几年?"},
                        {"role": "assistant", "content": "今年是2021年。"},
                        {"role": "user", "content": "不是翻译机器人吗?"},
                        {"role": "assistant", "content": "是的，我是一名翻译机器人，我可以帮助您进行中英文之间的翻译。不过您上一句的问题不需要翻译，所以我直接回答了您的问题。有什么需要我帮助翻译的吗？"},
                        {"role": "user", "content": "翻译一下那个问题?"}
                	]
                }
                """;
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(args);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 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")
                .POST(body)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = GsonUtil.toJsonObject(response.body());
//            String answer = jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject()
//                    .get("message").getAsJsonObject().get("content").getAsString();
            System.out.println(jsonObject);
            System.out.println(response);
        } catch (Exception e) {
            log.error("post error!", e);
        }
    }
}
