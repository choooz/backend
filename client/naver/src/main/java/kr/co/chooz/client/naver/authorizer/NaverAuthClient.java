package kr.co.chooz.client.naver.authorizer;

import kr.co.chooz.client.naver.response.NaverTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth-naver", url = "${auth.naver.url}")
public interface NaverAuthClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverTokenResponse generateToken(@RequestParam String grant_type,
                                     @RequestParam String client_id,
                                     @RequestParam String client_secret,
                                     @RequestParam String code,
                                     @RequestParam String state);

}
