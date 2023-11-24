package com.example.bilda_server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}
