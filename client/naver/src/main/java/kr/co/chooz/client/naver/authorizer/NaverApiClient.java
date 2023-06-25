package kr.co.chooz.client.naver.authorizer;

import kr.co.chooz.client.naver.response.NaverUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "api-naver", url = "${api.naver.url}")
public interface NaverApiClient {

    @GetMapping("/v1/nid/me")
    NaverUserInfo getUserInfo(@RequestHeader("Authorization") String authorization);

}
