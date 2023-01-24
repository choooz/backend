package com.example.manymanyUsers.exception;

public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    USER_NOT_FOUND(404,"USER_NOT_FOUND"),
    VOTE_NOT_FOUND(200,"VOTE_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}

