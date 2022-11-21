package com.example.manymanyUsers.config.oauth2.naver.controller;


import com.example.manymanyUsers.config.oauth2.naver.dto.GetnaverToken;
import com.example.manymanyUsers.config.oauth2.naver.service.NaverService;
import com.example.manymanyUsers.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class NaverController {

    private final NaverService naverService;
    private final UserService userService;

    @PostMapping("/naver")
    public ResponseEntity<String> getNaverJWTToken(@Valid @RequestBody GetnaverToken getnaverToken) throws IOException, ParseException {
        String code = getnaverToken.getCode();
        String state = getnaverToken.getState();
        String accessToken = naverService.NaverLogin(code, state);
        return ResponseEntity.ok(accessToken);
    }
}
