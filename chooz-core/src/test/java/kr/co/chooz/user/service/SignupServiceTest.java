package kr.co.chooz.user.service;

import kr.co.chooz.user.domain.GenderType;
import kr.co.chooz.user.domain.ProviderType;
import kr.co.chooz.user.domain.User;
import kr.co.chooz.user.dto.GeneralSignupInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignupServiceTest {


    private SignupService signupService;

    @BeforeEach
    void setUp() {
        signupService = new SignupService();
    }

    @Test
    void register() {

    }

    @ParameterizedTest
    @CsvSource(value = {
            "example1@Gamil.com,GOOGLE,providerId_1",
            "example2@kakao.com,KAKAO,providerId_2",
            "example3@naver.com,NAVER,providerId_3"
    }, delimiter = ',')
    void 외부에서_회원가입에_필요한_정보를_받는다(String email, ProviderType providerType, String providerId) {
        //given
        GeneralSignupInfo generalSignupInfo = new GeneralSignupInfo(email, providerType, providerId);

        //when
        User user = new User(generalSignupInfo);

        //then
        assertEquals(email, user.getEmail());
        assertEquals(providerType, user.getProvider());
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
        GeneralSignupInfo generalSignupInfo = new GeneralSignupInfo(email, providerType, providerId);
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            new User(generalSignupInfo);
        });
    }

    @Test
    void registerIfNeed() {
    }

    @AfterEach
    void tearDown() {
        signupService = null;
    }
}