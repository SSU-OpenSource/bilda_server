package com.example.bilda_server.service;

import com.example.bilda_server.domain.entity.Page;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.EvaluationItem;
import com.example.bilda_server.repository.PageRepository;
import com.example.bilda_server.response.PageAverageDTO;
import com.example.bilda_server.response.ScoreItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public PageAverageDTO getAverageScores(Long userId) {
        Page page = pageRepository.findByUserId(userId);
        User user = page.getUser();

        List<ScoreItemDTO> scoreItems = Arrays.stream(EvaluationItem.values())
                .map(item -> new ScoreItemDTO(item.getDescription(), page.getAverageScore(item), page.getHighScoreCount(item)))
                .toList();

        return new PageAverageDTO(user.getId(), user.getName(), scoreItems);
    }

}
