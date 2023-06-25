package kr.co.chooz.kakao;

import kr.co.chooz.kakao.response.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "api-kakao", url = "${api.kakao.url}")
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    KakaoUserInfo getUserInfo(@RequestHeader("Authorization") String authorization);
}
