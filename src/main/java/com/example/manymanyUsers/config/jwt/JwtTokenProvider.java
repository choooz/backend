package com.example.manymanyUsers.config.jwt;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.access-token.expire-length}")
    private Long accessTokenValidityInMilliseconds;

}
