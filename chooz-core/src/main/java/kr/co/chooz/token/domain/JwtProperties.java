package kr.co.chooz.token.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@Component
public class JwtProperties {

//    @Value("${jwt.issuer}")
    private final String issuer = "manyUser";

//    @Value("${jwt.secretKey}")
    private final String secretKey = "secretManyUser";

//    @Value("${jwt.tokenPrefix}")
    private final String tokenPrefix = "Bearer";
}
