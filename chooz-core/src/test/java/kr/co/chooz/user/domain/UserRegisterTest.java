package kr.co.chooz.user.domain;

import kr.co.chooz.support.ServiceIntegrationTest;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserReadRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.co.chooz.user.domain.entitiy.ProviderType.NORMAL;

@ServiceIntegrationTest
class UserRegisterTest {

    @Autowired
    private UserRegister userRegister;
    @Autowired
    private UserReadRepository userReadRepository;


    @DisplayName("일반 회원가입시 회원을 등록한다.")
    @Test
    void register() {
        //given
        String providerId = "providerID";

        //when
        userRegister.register(providerId, NORMAL);
        User findUser = userReadRepository.findByProviderId(providerId);

        //then
        Assertions.assertThat(findUser).extracting("providerId", "providerType")
                .containsExactlyInAnyOrder(providerId, NORMAL);
    }

    @DisplayName("일반 회원가입시 이미 등록되어 있는 유저의 경우 IllegalArgumentException을 throw 한다.")
    @Test
    void registerWhenUserIsAlreadyExist() {
        //given
        String providerId = "providerID";

        //when
        userRegister.register(providerId, NORMAL);

        //then
        Assertions.assertThatThrownBy(
                () -> userRegister.register(providerId, NORMAL)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("소셜 회원가입시 회원을 등록한다.")
    @Test
    void registerIfNeed() {
        //given
        String providerId = "providerID";

        //when
        userRegister.registerIfNeed(providerId, NORMAL);
        User findUser = userReadRepository.findByProviderId(providerId);

        //then
        Assertions.assertThat(findUser).extracting("providerId", "providerType")
                .containsExactlyInAnyOrder(providerId, NORMAL);
    }

    @DisplayName("소셜 회원가입시 이미 등록 되어 있는 유저의 경우 등록하지 않는다.")
    @Test
    void registerIfNeedWhenUserIsAlreadyExist() {
        //given
        String providerId = "providerID";
        userRegister.registerIfNeed(providerId, NORMAL);

        //when
        boolean isRegistered = userRegister.registerIfNeed(providerId, NORMAL);

        //then
        Assertions.assertThat(isRegistered).isFalse();
    }
}