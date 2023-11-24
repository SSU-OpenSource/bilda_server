package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.request.GPTCompletionChatRequest;
import com.example.bilda_server.request.GPTCompletionRequest;
import com.example.bilda_server.response.CompletionChatResponse;
import com.example.bilda_server.response.CompletionResponse;
import com.example.bilda_server.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CHAT_REQUEST_PREFIX)
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/completion")
    public CompletionResponse completion(final @RequestBody GPTCompletionRequest gptCompletionRequest) {

        return chatService.completion(gptCompletionRequest);
    }

    @PostMapping("/completion/chat")
    public CompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest gptCompletionChatRequest) {

        return chatService.completionChat(gptCompletionChatRequest);
    }

}