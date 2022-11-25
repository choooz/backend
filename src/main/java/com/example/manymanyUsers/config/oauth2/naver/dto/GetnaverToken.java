package com.example.manymanyUsers.config.oauth2.naver.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class GetnaverToken {
    @NotBlank
    String code;

    /**
     * redirectUrl 은 인가코드를 받아올 redirectUrl을 의미하며 여기서  redirectUrl은 카카오 로그인시 요청한 redirectUrl과 동일한 값으로 받아와야함
     */
    @NotBlank
    String state;


}
