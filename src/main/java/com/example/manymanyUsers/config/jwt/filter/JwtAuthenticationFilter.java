package com.example.manymanyUsers.config.jwt.filter;

import com.example.manymanyUsers.config.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@Getter
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenProvider jwtTokenProvider;

    @Builder
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String baseUrl = "http://localhost:8080/api";
        String requestURL = request.getRequestURL().toString();
        log.info("requestURL : " + requestURL);
        if(requestURL.equals(baseUrl + "/oauth/login")
                || requestURL.equals(baseUrl + "/user/addInfo")
                || requestURL.equals(baseUrl + "/user/addInterestCategory")
                || requestURL.contains(baseUrl + "/votes")
        ) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                try {
                    HashMap<String, Object> parseJwtTokenMap = jwtTokenProvider.parseJwtToken(authorizationHeader);
                    Claims claims = (Claims)parseJwtTokenMap.get("claims");
                    String token = (String) parseJwtTokenMap.get("token");
                    request.setAttribute("claims", claims); // jwt 정보 컨트롤러에서 사용할 수 있게 request에 담기
                    request.setAttribute("token",token);
                } catch (ExpiredJwtException jwtException) {
                    throw jwtException;
                }
        }
            filterChain.doFilter(request, response);
    }

}
