package com.example.manymanyUsers.token.controller;

import com.example.manymanyUsers.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public ResponseEntity reissueToken(String type, Long userId) {
        tokenService.issueRefreshToken(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
