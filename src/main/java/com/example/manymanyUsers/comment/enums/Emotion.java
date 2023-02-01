package com.example.manymanyUsers.comment.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum Emotion implements EnumModel {

    LIKE("좋아요"),
    HATE("싫어요");

    private String value;

    Emotion(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
