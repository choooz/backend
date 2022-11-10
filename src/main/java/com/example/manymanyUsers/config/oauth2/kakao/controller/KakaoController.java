package com.example.manymanyUsers.config.oauth2.kakao.controller;

import com.example.manymanyUsers.config.oauth2.kakao.dto.GetkakaoToken;
import com.example.manymanyUsers.config.oauth2.kakao.service.KakaoService;
import com.example.manymanyUsers.user.service.UserService;
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
     * @param getkakaoToken
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/kakao")
    public ResponseEntity<String> getToken(@Valid @RequestBody GetkakaoToken getkakaoToken) throws IOException, ParseException {
        String code = getkakaoToken.getCode();
        String redirectUrl = getkakaoToken.getRedirectUrl();
        String accessToken = kakaoService.KakaoLogin(code, redirectUrl);
        return ResponseEntity.ok(accessToken);
    }

//    @GetMapping("/login")
//    public UserInfoDTO login(@RequestAttribute Claims claims){
//        String email = (String) claims.get("email");
//        User user = userService.getUserInfo(email);
//        UserInfoDTO result = new UserInfoDTO();
//        result.setImgUrl(user.getImgUrl());
//        result.setEmail(user.getEmail());
//        result.setNickname(user.getNickname());
//        return result;
//    }

}
