package com.example.manymanyUsers.config.oauth2.naver.controller;


import com.example.manymanyUsers.config.oauth2.kakao.dto.GetLoginTokenResponse;
import com.example.manymanyUsers.config.oauth2.naver.dto.GetnaverToken;
import com.example.manymanyUsers.config.oauth2.naver.service.NaverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "naver", description = "naver 소셜 로그인 api")
public class NaverController {

    private final NaverService naverService;


    @Operation(summary = "네이버 로그인", description = "바디에 {code, state} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/naver")
    public ResponseEntity<GetLoginTokenResponse> getNaverToken(@Valid @RequestBody GetnaverToken getnaverToken) throws IOException, ParseException {
        String code = getnaverToken.getCode();
        String state = getnaverToken.getState();
        GetLoginTokenResponse getLoginTokenResponse = naverService.NaverLogin(code, state);
        return new ResponseEntity(getLoginTokenResponse, HttpStatus.OK);
    }

}
