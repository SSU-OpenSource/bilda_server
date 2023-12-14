package com.example.bilda_server.request;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GptCompletionChatRequest {

    private String model;

    private String role;

    private String message;

    private Integer maxTokens;


    public static ChatCompletionRequest of(GptCompletionChatRequest request) {
        return ChatCompletionRequest.builder()
            .model(request.getModel())
            .messages(convertChatMessage(request))
            .maxTokens(request.getMaxTokens())
            .build();
    }

    private static List<ChatMessage> convertChatMessage(GptCompletionChatRequest request) {
        return List.of(new ChatMessage(request.getRole(), request.getMessage()));
    }
}