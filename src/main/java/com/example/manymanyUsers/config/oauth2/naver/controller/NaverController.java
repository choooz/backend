package com.example.manymanyUsers.config.oauth2.naver.controller;


import com.example.manymanyUsers.config.oauth2.kakao.dto.GetLoginTokenResponse;
import com.example.manymanyUsers.config.oauth2.kakao.dto.GetUserInfo;
import com.example.manymanyUsers.config.oauth2.kakao.dto.TokenResponse;
import com.example.manymanyUsers.config.oauth2.naver.dto.GetnaverToken;
import com.example.manymanyUsers.config.oauth2.naver.service.NaverService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class NaverController {

    private final NaverService naverService;


    @PostMapping("/naver")
    public ResponseEntity<GetLoginTokenResponse> getNaverToken(@Valid @RequestBody GetnaverToken getnaverToken) throws IOException, ParseException {
        String code = getnaverToken.getCode();
        String state = getnaverToken.getState();
        GetLoginTokenResponse getLoginTokenResponse = naverService.NaverLogin(code, state);
        return new ResponseEntity(getLoginTokenResponse, HttpStatus.OK);
    }

}
