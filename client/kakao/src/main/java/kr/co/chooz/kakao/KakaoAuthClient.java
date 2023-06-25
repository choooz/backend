package kr.co.chooz.kakao;

import kr.co.chooz.kakao.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth-kakao", url = "${auth.kakao.url}")
public interface KakaoAuthClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse generateToken(@RequestParam String grant_type,
                                     @RequestParam String client_id,
                                     @RequestParam String redirect_uri,
                                     @RequestParam String code,
                                     @RequestParam String client_secret);
}
