package com.example.manymanyUsers.exception.user;

import com.example.manymanyUsers.exception.StatusEnum;
import javassist.NotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends NotFoundException {

    private final StatusEnum status;
    private static final String message = "해당 아이디를 가진 투표가 없습니다. 아이디 값을 다시 한번 확인하세요.";
    public UserNotFoundException() {
        super(message);
        this.status = StatusEnum.USER_NOT_FOUND;
    }
}
