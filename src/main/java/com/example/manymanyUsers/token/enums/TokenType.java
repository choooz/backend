package com.example.manymanyUsers.token.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum TokenType implements EnumModel {
    REFRESH("refresh"),
    ACCESS("access");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
