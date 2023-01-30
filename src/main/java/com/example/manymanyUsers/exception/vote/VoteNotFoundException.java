package com.example.manymanyUsers.exception.vote;

import com.example.manymanyUsers.exception.StatusEnum;
import javassist.NotFoundException;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class VoteNotFoundException extends NotFoundException {

    private final StatusEnum status;

    private static final String message = "해당 id를 가진 투표가 없습니다. 아이디 값을 다시 한번 확인하세요.";

    public VoteNotFoundException() {
        super(message);
        this.status = StatusEnum.VOTE_NOT_FOUND;
    }

}
