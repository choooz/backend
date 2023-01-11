package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum Age implements EnumModel {
    teenager("10대"),
    twenties("20대"),
    thirties("30대"),
    tourties("40대"),
    fifties("50대"),
    NULL("null");

    private String value;

    Age(String value) {
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
