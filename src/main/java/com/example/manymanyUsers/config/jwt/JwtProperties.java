package com.example.manymanyUsers.config.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.security.Key;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@Component
public class JwtProperties {
    @Value("${issuer}")
    private String issuer;

    @Value("${secretKey}")
    private Key secretKey;

    @Value("${tokenPrefix}")
    private String tokenPrefix;
}
