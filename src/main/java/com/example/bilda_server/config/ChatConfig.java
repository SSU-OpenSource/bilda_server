package com.example.bilda_server.config;

import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Value("${gpt.token}")
    private String token;

    @Bean
    public OpenAiService openAiService(){
        return new OpenAiService(token, Duration.ofSeconds(60));
    }

}