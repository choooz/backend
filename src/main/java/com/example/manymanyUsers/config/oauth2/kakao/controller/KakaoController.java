package com.example.manymanyUsers.config.oauth2.kakao.controller;

import com.example.manymanyUsers.config.oauth2.kakao.dto.GetLoginTokenResponse;
import com.example.manymanyUsers.config.oauth2.kakao.dto.GetUserInfoResponse;
import com.example.manymanyUsers.config.oauth2.kakao.dto.GetkakaoTokenRequest;
import com.example.manymanyUsers.config.oauth2.kakao.service.KakaoService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "kakao", description = "카카오 소셜 로그인 api")
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

    @Operation(summary = "카카오 로그인", description = "바디에 {code, redirectUrl} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/kakao")
    public ResponseEntity<GetLoginTokenResponse> getKaKaoToken(@Valid @RequestBody GetkakaoTokenRequest getkakaoToken) throws IOException, ParseException {
        String code = getkakaoToken.getCode();
        String redirectUrl = getkakaoToken.getRedirectUrl();
        GetLoginTokenResponse getLoginToken = kakaoService.KakaoLogin(code, redirectUrl);
        return new ResponseEntity(getLoginToken, HttpStatus.OK);
    }

    @Operation(summary = "유저 정보", description = "헤더에 토큰 담아 보내주시면 됩니다.")
    @GetMapping("/login")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(@RequestAttribute Claims claims) {
        //엑세스 토큰안의 유저 아이디로 유저를 찾은 다음 유저정보 리턴해줌
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        //userId로 유저 꺼내기
        Optional<User> result = userRepository.findById(longId);
        User user = result.get();

        GetUserInfoResponse getUserInfo = GetUserInfoResponse.builder()
                .username(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .provider(user.getProvider())
                .userId(user.getId())
                .age(user.getAge())
                .gender(user.getGender())
                .mbti(user.getMbti())
                .interestedCategory(user.getCategoryLists())
                .message("유저 정보 요청에 성공했습니다.")
                .build();

        return new ResponseEntity(getUserInfo, HttpStatus.OK);
    }
}
