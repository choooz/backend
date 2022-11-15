package com.example.manymanyUsers.config.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // swagger-ui.html의 경우 인증된 사용자가 아니어도 접근가능하도록 설정(dev환경에 대해서만 swagger 설정을 하였기 때문에 인증된 사용자가 아니어도 됨)
        return (web) -> web.ignoring().antMatchers("/swagger-ui/**","v1-definition/**");
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()//기본 로그인 페이지 사용x , rest API 만들기 위해 필요
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll() // preflight 대응
                .antMatchers("/api/**","/swagger-ui/**","v1-definition/**").permitAll() // /api/**에 대한 접근을 인증 절차 없이 허용(로그인 관련 url)
                // 특정 권한을 가진 사용자만 접근을 허용해야 할 경우, 하기 항목을 통해 가능
                //.antMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated() // 위에서 따로 지정한 접근허용 리소스 설정 후 그 외 나머지 리소스들은 무조건 인증을 완료해야 접근 가능
                .and()
                .headers() // 아래에 X-Frame-Option 헤더 설정을 위해 headers() 작성
                .frameOptions().sameOrigin() // 동일 도메인에서는 iframe 접근 가능하도록 X-Frame-Options을 smaeOrigin()으로 설정
                .and()
                .cors()
                .and()
                .csrf().disable();
                // 예외 처리를 하고 싶다면 아래와 같이 작성 가능합니다.
                //.exceptionHandling() // 예외 처리 지정
                //.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                //.accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // 커스텀 필터 등록하며, 기존에 지정된 필터에 앞서 실행
        return http.build();
    }

    //passwordEncoder
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
