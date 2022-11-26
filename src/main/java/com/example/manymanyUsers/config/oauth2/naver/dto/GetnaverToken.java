package com.example.manymanyUsers.config.oauth2.naver.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class GetnaverToken {
    @NotBlank
    String code;

    @NotBlank
    String state;

}
