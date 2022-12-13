package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum Gender implements EnumModel {
    MALE("남자"),
    FEMALE("여자");

    private String value;

    Gender(String value) {
        this.value = value;
    }


    /**
     * enum key 리턴
     * @return 'Male' or 'FEMALE'
     */
    @Override
    public String getKey() {
        return name();
    }

    /**
     * enum value 리턴
     * @return '남자' or '여자'
     */
    @Override
    public String getValue() {
        return value;
    }
}
