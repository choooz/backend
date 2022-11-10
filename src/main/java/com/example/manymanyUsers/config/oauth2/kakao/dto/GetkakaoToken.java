package com.example.manymanyUsers.config.oauth2.kakao.dto;


import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Getter
public class GetkakaoToken {
    @NotBlank
    String code;

    @NotBlank
    String redirectUrl;
}
