package com.example.bilda_server.service;

import com.example.bilda_server.domain.entity.Answer;
import com.example.bilda_server.domain.entity.Question;
import com.example.bilda_server.repository.AnswerRepository;
import com.example.bilda_server.repository.QuestionRepository;
import com.example.bilda_server.request.GPTCompletionChatRequest;
import com.example.bilda_server.request.GPTCompletionRequest;
import com.example.bilda_server.response.CompletionChatResponse;
import com.example.bilda_server.response.CompletionResponse;
import com.example.bilda_server.response.CompletionResponse.Message;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final OpenAiService openAiService;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @Transactional
    public CompletionResponse completion(final GPTCompletionRequest restRequest) {
        CompletionResult result = openAiService.createCompletion(GPTCompletionRequest.of(restRequest));
        CompletionResponse response = CompletionResponse.of(result);

        List<String> messages = response.getMessages().stream()
            .map(Message::getText)
            .collect(Collectors.toList());

        Answer answer = saveAnswer(messages);
        saveQuestion(restRequest.getPrompt(), answer);

        return response;
    }

    @Transactional
    public CompletionChatResponse completionChat(GPTCompletionChatRequest gptCompletionChatRequest) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GPTCompletionChatRequest.of(gptCompletionChatRequest));

        CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);

        List<String> messages = response.getMessages().stream()
            .map(CompletionChatResponse.Message::getMessage)
            .collect(Collectors.toList());

        Answer answer = saveAnswer(messages);

        saveQuestion(gptCompletionChatRequest.getMessage(), answer);

        return response;
    }

    private void saveQuestion(String question, Answer answer) {
        Question questionEntity = new Question(question, answer);
        questionRepository.save(questionEntity);
    }

    private Answer saveAnswer(List<String> response) {

        String answer = response.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.joining());

        return answerRepository.save(new Answer(answer));
    }

}