package kr.co.chooz.user.controller;

import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.port.in.UserUseCase;
import kr.co.chooz.user.request.KakaoLoginRequest;
import kr.co.chooz.user.request.NaverLoginRequest;
import kr.co.chooz.user.response.TokenResponse;
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
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginToken loginToken = userUserCase.signupByThirdParty(kakaoLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @GetMapping("/additional-info")
    public ResponseEntity addUserInfo(@RequestAttribute Long userId) {
        userUserCase.addUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signup/naver")
    public ResponseEntity<TokenResponse> naverLogin(@Valid @RequestBody NaverLoginRequest naverLoginRequest) {
        LoginToken loginToken = userUserCase.signupByThirdParty(naverLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }
}
