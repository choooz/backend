package kr.co.chooz.user.domain;

import kr.co.chooz.common.EnumModel;

public enum ProviderType implements EnumModel {

    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");


    private String value;

    ProviderType(String value) {
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
