package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum Choice implements EnumModel {
    A("A"),
    B("B");

    private String value;

    Choice(String value) {
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
