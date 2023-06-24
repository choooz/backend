package kr.co.chooz.user.controller;

import kr.co.chooz.user.dto.SocialLoginRequest;
import kr.co.chooz.user.dto.TokenResponse;
import kr.co.chooz.user.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {
    private final UserUseCase userUserCase;

    /*
    클라이언트로 부터 인가 코드를 받아서 유저 정보 받아오고 -> 카카오 서버에 인가 코드로 요청 보내 -> 카카오 서버가 반환해준 유저정보로 로그인 진행
     */
    public ResponseEntity<TokenResponse> socialLogin(SocialLoginRequest socialLoginRequest) {
        userUserCase.signupByThirdParty() // 카카오 서버가 반환해준 유저 정보로 로그인 진행
    }

}
