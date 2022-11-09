package com.example.manymanyUsers.config.oauth2.service;

import com.example.manymanyUsers.config.jwt.JwtTokenProvider;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.LoginResponse;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private final String clientId;

    @Transactional
    public LoginResponse login(String providerName, String code) throws ParseException {

        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        String kakaoToken = getKakaoToken(code, "http://localhost:8080/oauth/kakao");


    }

    /**
     * 인가코드로 카카오서버에 엑세스 토큰 요청받는 함수 -> 추후에 다른 oauth2 서비스들도 요청할수있게 수정 필요
     * @param code          : 인가코드
     * @param redirectUrl   : 엑세스 토큰 받을 리다이렉트 url
     * @return              : 엑세스 토큰
     * @throws ParseException
     */
    public String getKakaoToken(String code, String redirectUrl) throws ParseException {
        // 인가코드로 토큰받기
        String host = "https://kauth.kakao.com/oauth/token";

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("client_id", clientId);
//        param.add("redirect_uri", "http://localhost:3000/login");

        // TODO : Redirect Url 파라미터로 받아서 적용
        //        로컬, 개발, 운영 서버 테스트에서 계속 변경할 수 없음
        param.add("redirect_uri", redirectUrl);
        param.add("code", code);

        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(param, headers);
        ResponseEntity<String> res = rt.exchange(host,
                HttpMethod.POST,
                req,
                String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject parse = (JSONObject) jsonParser.parse(res.getBody());

        return (String) parse.get("access_token");
    }

    /**
     * 카카오 서버로 받은 엑세스 토큰으로 유저정보 받아오는 함수
     * @param access_token   : 카카오 서버로 부터 받은 엑세스 토큰
     * @return               : 유저정보
     */
    public Map<String, String> getKaKaoUserInfo(String access_token) {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, String> result = new HashMap<>(); //key, value json 형식으로 데이터 내보내기 위해 hashMap 사용

        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
                String line;
                StringBuilder res = new StringBuilder();
                while((line=br.readLine())!=null)
                {
                    res.append(line);
                }

                System.out.println("res = " + res);

                JSONParser parser = new JSONParser();
                org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(res.toString());

                org.json.simple.JSONObject properties = (org.json.simple.JSONObject) obj.get("properties");

                String id = obj.get("id").toString();
                String nickname = properties.get("nickname").toString();
                String profile_image = properties.get("profile_image").toString();

                result.put("id", id);
                result.put("nickname", nickname);
                result.put("profile_image", profile_image);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }




}
