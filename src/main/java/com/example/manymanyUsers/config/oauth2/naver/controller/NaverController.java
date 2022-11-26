package com.example.manymanyUsers.config.oauth2.naver.controller;


import com.example.manymanyUsers.config.oauth2.naver.dto.GetUserInfo;
import com.example.manymanyUsers.config.oauth2.naver.dto.GetnaverToken;
import com.example.manymanyUsers.config.oauth2.naver.service.NaverService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class NaverController {

    private final UserRepository userRepository;
    private final NaverService naverService;


    @PostMapping("/naver")
    public ResponseEntity<String> getNaverToken(@Valid @RequestBody GetnaverToken getnaverToken) throws IOException, ParseException {
        String code = getnaverToken.getCode();
        String state = getnaverToken.getState();
        String accessToken = naverService.NaverLogin(code, state);
        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/naverLogin")
    public ResponseEntity<GetUserInfo> getUserInfo(@RequestAttribute Claims claims) {

        String providerId = (String) claims.get("providerId");
        //provderId로 유저 꺼내기
        Optional<User> result = userRepository.findByProviderId(providerId);
        User user = result.get();
        GetUserInfo getUserInfo = GetUserInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .provider(user.getProvider())
                .providerId(user.getProviderId()).build();
        return ResponseEntity.ok(getUserInfo);
    }

}
