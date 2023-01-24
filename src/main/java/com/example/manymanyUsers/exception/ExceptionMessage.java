package com.example.manymanyUsers.exception;

import lombok.*;

@AllArgsConstructor
@Builder
public class ExceptionMessage {
    private StatusEnum status;
    private String msg;

    public static ExceptionMessage of(StatusEnum status, String message){
        return ExceptionMessage.builder().status(status)
                .msg(message)
                .build();
    }

    public static ExceptionMessage of(String msg){
        return ExceptionMessage.of(StatusEnum.BAD_REQUEST,msg);
    }
}
