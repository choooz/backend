package kr.co.chooz.config;

import kr.co.chooz.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebFilterConfig {

    private static final String[] filterUrls = {"/api/users/userInfo"};

    @Bean
    public FilterRegistrationBean JwtFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(jwtAuthenticationFilter);

        filterRegistrationBean.addUrlPatterns(filterUrls);
        return filterRegistrationBean;
    }
}
