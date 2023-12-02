package com.example.bilda_server.service;

import com.example.bilda_server.domain.entity.Answer;
import com.example.bilda_server.domain.entity.Question;
import com.example.bilda_server.repository.AnswerRepository;
import com.example.bilda_server.repository.QuestionRepository;
import com.example.bilda_server.request.GptCompletionChatRequest;
import com.example.bilda_server.response.GptCompletionChatResponse;
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
    public GptCompletionChatResponse completionChat(
        GptCompletionChatRequest gptCompletionChatRequest) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GptCompletionChatRequest.of(gptCompletionChatRequest));

        GptCompletionChatResponse response = GptCompletionChatResponse.of(chatCompletion);

        List<String> messages = response.getMessages().stream()
            .map(GptCompletionChatResponse.Message::getMessage)
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