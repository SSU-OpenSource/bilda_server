package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.request.GptCompletionChatRequest;
import com.example.bilda_server.response.GptCompletionChatResponse;
import com.example.bilda_server.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(apiVersion)
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @Operation(summary = "챗봇 메세지 발송", description = "챗봇과 메세지를 주고 받을 수 있습니다.", tags = {
        "ChatController"})
    @PostMapping("/completion/chat")
    public GptCompletionChatResponse completionChat(
        final @RequestBody GptCompletionChatRequest gptCompletionChatRequest) {
        return chatService.completionChat(gptCompletionChatRequest);
    }
}