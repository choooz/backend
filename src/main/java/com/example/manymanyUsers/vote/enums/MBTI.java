package com.example.manymanyUsers.vote.enums;

import com.example.manymanyUsers.common.enums.EnumModel;

public enum MBTI implements EnumModel{

    ENFJ("enfj"),
    ENFP("enfp"),
    ENTJ("entj"),
    ENTP("entp"),
    ESFJ("esfj"),
    ESFP("esfp"),
    ESTJ("estj"),
    ESTP("estp"),
    INFJ("infj"),
    INFP("infp"),
    INTJ("intj"),
    INTP("intp"),
    ISFJ("isfj"),
    ISFP("isfp"),
    ISTJ("istj"),
    ISTP("istp");

    private String value;

    MBTI(String value) {
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
