package com.example.bilda_server.utils;

public final class RequestURI {

    private static final String apiVersion = "/api/v1/";
    public static final String USER_REQUEST_PREFIX = apiVersion + "users";
    public static final String SUBJECT_REQUEST_PREFIX = apiVersion + "subject";
    public static final String EMAIL_REQUEST_PREFIX = apiVersion + "email";
    public static final String CHAT_REQUEST_PREFIX = apiVersion + "chat";

    private RequestURI() {
    }
}
