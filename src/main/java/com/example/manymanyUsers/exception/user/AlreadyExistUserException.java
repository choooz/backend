package com.example.manymanyUsers.exception.user;

import com.example.manymanyUsers.exception.StatusEnum;
import lombok.Getter;

@Getter
public class AlreadyExistUserException extends RuntimeException {

    private final StatusEnum status;

    private static final String message = "유저의 정보가 이미 존재합니다";

    public AlreadyExistUserException(StatusEnum status) {
        super(message);
        this.status = status;
    }
}
