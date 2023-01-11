package com.example.manymanyUsers.config.oauth2.kakao.controller;

import com.example.manymanyUsers.config.oauth2.kakao.dto.GetUserInfo;
import com.example.manymanyUsers.config.oauth2.kakao.dto.GetkakaoToken;
import com.example.manymanyUsers.config.oauth2.kakao.dto.TokenResponse;
import com.example.manymanyUsers.config.oauth2.kakao.service.KakaoService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    /**
     * 카카오 서버에서 유저정보 조희
     *
     * @param getkakaoToken
     * @return 엑세스 토큰
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/kakao")
    public ResponseEntity<TokenResponse> getKaKaoToken(@Valid @RequestBody GetkakaoToken getkakaoToken) throws IOException, ParseException {
        String code = getkakaoToken.getCode();
        String redirectUrl = getkakaoToken.getRedirectUrl();
        String accessToken = kakaoService.KakaoLogin(code, redirectUrl);
        TokenResponse tokenResponse = TokenResponse.builder()
                .token(accessToken)
                .build();
        return new ResponseEntity(tokenResponse, HttpStatus.OK);
    }


    @GetMapping("/login")
    public ResponseEntity<GetUserInfo> getUserInfo(@RequestAttribute Claims claims) {
        //엑세스 토큰안의 유저 아이디로 유저를 찾은 다음 유저정보 리턴해줌
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        //userId로 유저 꺼내기
        Optional<User> result = userRepository.findById(longId);
        User user = result.get();
        boolean isNewUser = false;
        if(user.getAge().equals(0) && user.getGender().equals(Gender.NULL) && user.getMbti().equals(MBTI.NULL)){
            isNewUser = true;
        }

        GetUserInfo getUserInfo = GetUserInfo.builder()
                .username(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .provider(user.getProvider())
                .userId(user.getId())
                .age(user.getAge())
                .gender(user.getGender())
                .mbti(user.getMbti())
                .isNewUser(isNewUser)
                .message("유저 정보 요청에 성공했습니다.")
                .build();

        return new ResponseEntity(getUserInfo, HttpStatus.OK);
    }
}
