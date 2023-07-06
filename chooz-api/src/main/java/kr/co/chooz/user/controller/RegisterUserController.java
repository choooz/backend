package kr.co.chooz.user.controller;

import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.dto.SocialLoginRequest;
import kr.co.chooz.user.dto.TokenResponse;
import kr.co.chooz.user.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RegisterUserController {
    private final UserUseCase userUserCase;

    @PostMapping("/signup/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody SocialLoginRequest socialLoginRequest) {
        LoginToken loginToken = userUserCase.signupByThirdParty(socialLoginRequest.toKakaoDomain());
        return new ResponseEntity<>(new TokenResponse(loginToken), HttpStatus.OK);
    }
}
