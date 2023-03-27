package com.example.manymanyUsers.token.controller;

import com.example.manymanyUsers.token.TokenResponse;
import com.example.manymanyUsers.token.enums.TokenType;
import com.example.manymanyUsers.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
@Tag(name = "token", description = "토큰 재발급 api")
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "토큰 재발급", description = "enum 형태로 TokenType, String 타입으로 refreshToken 형태로 보내주시면 됩니다")
    @GetMapping("")
    public ResponseEntity<TokenResponse> reissueToken(TokenType tokenType, String refreshToken) {
        if(tokenType == TokenType.ACCESS){
            String acessToken = tokenService.refreshAcessToken(refreshToken);
            TokenResponse tokenResponse = new TokenResponse(acessToken, tokenType);
            return new ResponseEntity(tokenResponse,HttpStatus.OK);
        }
        String newRefreshToken = tokenService.updateRefreshToken(refreshToken);
        TokenResponse tokenResponse = new TokenResponse(newRefreshToken, tokenType);
        return new ResponseEntity(tokenResponse ,HttpStatus.OK);
    }

}
