package com.example.bilda_server.exception;

import com.example.bilda_server.response.ResponseDto;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        IllegalArgumentException.class,
        RuntimeException.class,
        IllegalStateException.class,
        IOException.class
    })
    public ResponseDto<?> handler(Exception e) {
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}

