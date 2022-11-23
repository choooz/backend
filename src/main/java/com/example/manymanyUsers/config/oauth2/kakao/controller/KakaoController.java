package com.example.manymanyUsers.config.oauth2.kakao.controller;

import com.example.manymanyUsers.config.oauth2.kakao.dto.GetUserInfo;
import com.example.manymanyUsers.config.oauth2.kakao.dto.GetkakaoToken;
import com.example.manymanyUsers.config.oauth2.kakao.service.KakaoService;
import com.example.manymanyUsers.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    /**
     * 카카오 서버에서 유저정보 조희
     *
     * @param getkakaoToken
     * @return 엑세스 토큰
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/kakao")
    public ResponseEntity<String> getKaKaoToken(@Valid @RequestBody GetkakaoToken getkakaoToken) throws IOException, ParseException {
        String code = getkakaoToken.getCode();
        String redirectUrl = getkakaoToken.getRedirectUrl();
        String accessToken = kakaoService.KakaoLogin(code, redirectUrl);
        return ResponseEntity.ok(accessToken);
    }


    @GetMapping("/kakaoLogin")
    public ResponseEntity<GetUserInfo> getUserInfo(@RequestAttribute Claims claims) {
        //엑세스 토큰안의 유저 이메일로 유저를 찾은 다음 유저정보 리턴해줌
        String email = (String) claims.get("email");
        userService
    }
}
