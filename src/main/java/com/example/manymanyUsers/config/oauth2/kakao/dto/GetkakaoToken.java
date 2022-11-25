package com.example.manymanyUsers.config.oauth2.kakao.dto;


import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Getter
public class GetkakaoToken {
    /**
     * 인가 코드 (인가코드는 1회용으로 요청을 한번 보냈으면 에러가 나기 때문에 요청을 다시 받아야함)
     */
    @NotBlank
    String code;

    /**
     * redirectUrl 은 인가코드를 받아올 redirectUrl을 의미하며 여기서  redirectUrl은 카카오 로그인시 요청한 redirectUrl과 동일한 값으로 받아와야함
     * 리다이렉트 유알엘을 받는 이유는 로컬, 배포 , 테스트 환경에서 유동적으로 실행할수있게 하기 위함임
     */
    @NotBlank
    String redirectUrl;
}
