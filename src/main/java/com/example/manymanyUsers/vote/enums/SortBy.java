package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum SortBy implements EnumModel {
    ByTime("createdDate"),
    ByPopularity("인기순");

    private String value;

    SortBy(String value) {
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
