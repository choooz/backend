package com.example.manymanyUsers.token;

import com.example.manymanyUsers.token.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private TokenType tokenType;
}
