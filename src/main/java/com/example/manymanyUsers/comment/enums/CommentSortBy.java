package com.example.manymanyUsers.comment.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum CommentSortBy implements EnumModel {
    ByTime("최신순"),
    ByPopularity("인기순");

    private String value;

    CommentSortBy(String value) {
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
