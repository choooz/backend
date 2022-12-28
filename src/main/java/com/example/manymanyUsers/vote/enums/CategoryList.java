package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum CategoryList implements EnumModel {

    FOODS("음식"),
    CARRIER("진로"),
    LOVE("연애"),
    FASHION("패션"),
    INTEREST("재미"),
    NULL("null");

    private String value;

    CategoryList(String value) {
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
