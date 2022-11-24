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

    private final String issuer = "manyUser";

    private final String secretKey = "secretManyUser";

    private final String tokenPrefix = "Bearer";
}
