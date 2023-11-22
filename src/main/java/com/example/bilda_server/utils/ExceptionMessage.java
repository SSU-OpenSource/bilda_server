package com.example.bilda_server.utils;

public final class ExceptionMessage {

    public static final String USER_NOT_FOUND_BY_EMAIL = "해당 이메일로 가입된 계정이 없습니다.";
    public static final String USER_ALREADY_EXIST = "이미 해당 이메일로 가입된 계정이 있습니다.";
    public static final String LOGIN_FAILURE = "해당 이메일 또는 비밀번호를 잘못 입력하셨습니다.";
    public static final String EXPIRED_PERIOD_REFRESH_TOKEN = "기한이 만료된 RefreshToken입니다.";
    public static final String EXPIRED_PERIOD_ACCESS_TOKEN = "기한이 만료된 AccessToken입니다.";
    public static final String INVALID_REFRESH_TOKEN = "올바르지 않은 형식의 RefreshToken입니다.";
    public static final String INVALID_ACCESS_TOKEN = "올바르지 않은 형식의 AccessToken입니다.";


    private ExceptionMessage() {
    }
}
