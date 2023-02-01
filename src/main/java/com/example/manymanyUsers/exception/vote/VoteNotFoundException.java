package com.example.manymanyUsers.exception.vote;

import com.example.manymanyUsers.exception.StatusEnum;
import lombok.Getter;

@Getter
public class VoteNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 id를 가진 투표가 없습니다. 아이디 값을 다시 한번 확인하세요.";

    public VoteNotFoundException() {
        super(message);
        this.status = StatusEnum.VOTE_NOT_FOUND;
    }

}
