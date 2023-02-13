package com.example.manymanyUsers.exception.vote;

import com.example.manymanyUsers.exception.StatusEnum;
import lombok.Getter;

@Getter
public class AlreadyUserDoVoteException extends RuntimeException{

    private final StatusEnum status;

    private static final String message = "동일 유저가 해당 투표를 이미 참여했습니다";

    public AlreadyUserDoVoteException() {
        super(message);
        this.status = StatusEnum.ALREADY_VOTE_RESULT_EXIST;
    }

}
