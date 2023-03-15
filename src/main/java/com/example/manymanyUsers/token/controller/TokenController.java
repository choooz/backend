package com.example.manymanyUsers.token.controller;

import com.example.manymanyUsers.token.TokenResponse;
import com.example.manymanyUsers.token.enums.TokenType;
import com.example.manymanyUsers.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

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
