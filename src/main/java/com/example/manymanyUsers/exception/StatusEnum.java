package com.example.manymanyUsers.exception;

public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    USER_NOT_FOUND(404,"USER_NOT_FOUND"),
    VOTE_NOT_FOUND(200,"VOTE_NOT_FOUND"),
    COMMENT_NOT_FOUND(404,"COMMENT_NOT_FOUND"),
    ALREADY_VOTE_RESULT_EXIST(403, "ALREADY_VOTE_RESULT_EXIST"),
    TOKEN_NOT_EXIST(401, "TOKEN_NOT_EXIST");

    private final int statusCode;
    private final String code;

    private StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}

