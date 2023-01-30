package com.example.manymanyUsers.exception.vote;

import com.example.manymanyUsers.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class VoteExceptionHandler {

    @ExceptionHandler(VoteNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(VoteNotFoundException e) {
        final ExceptionMessage message = ExceptionMessage.of(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
