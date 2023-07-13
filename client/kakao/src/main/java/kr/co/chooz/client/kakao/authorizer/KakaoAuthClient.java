package kr.co.chooz.client.kakao.authorizer;

import kr.co.chooz.client.kakao.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth-kakao", url = "${auth.kakao.url}")
public interface KakaoAuthClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse generateToken(@RequestParam(name = "grant_type") String grantType,
                                     @RequestParam(name = "client_id") String clientId,
                                     @RequestParam(name = "redirect_uri") String redirectUri,
                                     @RequestParam(name = "code") String code,
                                     @RequestParam(name = "client_secret") String clientSecret);
}
