package kr.co.chooz.user.domain;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.dto.LoginRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @ParameterizedTest
    @CsvSource(value = {
            "example1@Gamil.com,GOOGLE,providerId_1",
            "example2@kakao.com,KAKAO,providerId_2",
            "example3@naver.com,NAVER,providerId_3"
    }, delimiter = ',')
    void 외부에서_회원가입에_필요한_정보를_받는다(String email, ProviderType providerType, String providerId) {
        //given
        LoginRequest loginRequest = new LoginRequest(email, providerType, providerId);

        //when
        User user = new User(loginRequest);

        //then
        assertEquals(email, user.getEmail());
        assertEquals(providerType, user.getProviderType());
        assertEquals(providerId, user.getProviderId());
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",GOOGLE,providerId_1",
            "example2@kakao.com,,providerId_2",
            "example3@naver.com,NAVER,"
    }, delimiter = ',')
    void 회원가입에_필요한_정보중_필수값이_누락되면_IllegalArgumentException을_터트린다(String email, ProviderType providerType, String providerId) {
        //given
        LoginRequest loginRequest = new LoginRequest(email, providerType, providerId);
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            new User(loginRequest);
        });
    }

}