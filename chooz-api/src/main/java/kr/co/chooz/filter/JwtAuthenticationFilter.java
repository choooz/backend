package kr.co.chooz.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import kr.co.chooz.token.domain.JwtTokenProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Getter
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        setResponseHeader(response);

        if (isPreflightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            parseTokenAndTransferUserId(request, authorizationHeader);
        } catch (ExpiredJwtException jwtException) {
            proceedWhenTokenExpired(response);
        } catch (JwtException | IllegalArgumentException exception) {
            log.info("jwtException : {}", exception);
            throw exception;
        }

        filterChain.doFilter(request, response);

    }

    private static void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Content-Type", "*");
    }

    private static boolean isPreflightRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private void parseTokenAndTransferUserId(HttpServletRequest request, String authorizationHeader) {
        HashMap<String, Object> parseJwtTokenMap = jwtTokenProvider.parseJwtToken(authorizationHeader);
        Integer userId = getUserIdFromToken(parseJwtTokenMap);
        request.setAttribute("userId", userId);
    }

    private static Integer getUserIdFromToken(HashMap<String, Object> parseJwtTokenMap) {
        Claims claims = (Claims) parseJwtTokenMap.get("claims");
        return (Integer) claims.get("userId");
    }

    private static void proceedWhenTokenExpired(HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        setResponseHeaderWhenTokenExpired(response);
        ResponseStatusException responseStatusException = new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        mapper.writeValue(response.getWriter(), responseStatusException);
    }

    private static void setResponseHeaderWhenTokenExpired(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }
}
