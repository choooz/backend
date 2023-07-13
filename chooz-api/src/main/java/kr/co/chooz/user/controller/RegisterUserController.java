package kr.co.chooz.user.controller;

import io.jsonwebtoken.Claims;
import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.dto.SocialLoginRequest;
import kr.co.chooz.user.dto.TokenResponse;
import kr.co.chooz.user.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class RegisterUserController {
    private final UserUseCase userUserCase;

    @PostMapping("/signup/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody SocialLoginRequest socialLoginRequest) {
        LoginToken loginToken = userUserCase.signupByThirdParty(socialLoginRequest.toKakaoDomain());
        return new ResponseEntity<>(new TokenResponse(loginToken), HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<Claims> getUserInfo(@RequestAttribute Claims claims) {
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }
}
