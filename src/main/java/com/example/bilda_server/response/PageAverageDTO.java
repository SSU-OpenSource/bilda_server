package com.example.bilda_server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageAverageDTO {
    private Long userId;
    private String userName;
    private String nickName;
    private List<ScoreItemDTO> scoreItems;
}
