package com.example.manymanyUsers.exception.user;

import com.example.manymanyUsers.exception.StatusEnum;
import lombok.Getter;

@Getter
public class UserIllegalStateException extends IllegalStateException {
    private final StatusEnum status;
    private static final String message = "MBTI 수정 후 2개월 내에 수정할 수 없습니다.";

    public UserIllegalStateException() {
        super(message);
        this.status = StatusEnum.BAD_REQUEST;
    }
}
