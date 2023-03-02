package com.jonas.ai;

import com.jonas.ai.service.OpenAiService;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.http.HttpResponse;


@SpringBootTest
@RunWith(SpringRunner.class)
class ApplicationTests {

	@Autowired
	private OpenAiService openAiService;

	@Test
	void testChat1() {
		HttpResponse<String> response = openAiService.chat("pip常用指令");
		System.out.println(response);
	}

	@Test
	void testChat2() {
		Response response = openAiService.chat2("pip常用指令");
		System.out.println(response);
	}

}
