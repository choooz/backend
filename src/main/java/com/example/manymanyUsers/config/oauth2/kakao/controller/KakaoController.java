package com.example.manymanyUsers.config.oauth2.kakao.controller;

import com.example.manymanyUsers.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/api/oauth")
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    /**
     * 카카오 서버에서 유저정보 조회
     * @param code          : 인가 코드
     * @return accessToken : 엑세스 토큰
     * @throws IOException
     */
    @GetMapping("/kakao")
    public String getToken(@RequestParam(value = "code") String code, @RequestParam(value = "redirectUrl") String redirectUrl) throws IOException, ParseException {
        return kakaoService.KakaoLogin(code, redirectUrl);
    }

}
