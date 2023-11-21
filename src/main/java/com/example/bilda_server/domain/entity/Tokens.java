package com.example.bilda_server.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Tokens {

    private final String accessToken;
    private final String refreshToken;
}
