package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum Category implements EnumModel {

    FOODS("음식"),
    CAREER("진로"),
    LOVE("연애"),
    FASHION("패션"),
    INTEREST("재미"),
    ETC("기타");

    private String value;

    Category(String value) {
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
