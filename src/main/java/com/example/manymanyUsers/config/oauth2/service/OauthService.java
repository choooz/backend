package com.example.manymanyUsers.config.oauth2.service;

import com.example.manymanyUsers.config.jwt.JwtTokenProvider;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(String providerName, String code) {

    }

}
