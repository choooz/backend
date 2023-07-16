package kr.co.chooz.user.domain;

import kr.co.chooz.support.ServiceTest;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserPersistencePort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.co.chooz.user.domain.entitiy.ProviderType.NORMAL;

@ServiceTest
class UserRegisterTest {

    @Autowired
    private UserRegister userRegister;
    @Autowired
    private UserPersistencePort userPersistencePort;

    @DisplayName("일반 회원가입시 회원을 등록한다.")
    @Test
    void register() {
        //given
        String providerId = "providerID";

        //when
        userRegister.register(providerId, NORMAL);
        User findUser = userPersistencePort.findByProviderId(providerId);

        //then
        Assertions.assertThat(findUser).extracting("providerId", "providerType")
                .containsExactlyInAnyOrder(providerId, NORMAL);
    }



    @DisplayName("소셜 회원가입시 회원을 등록한다.")
    @Test
    void registerIfNeed(){
        //given
        String providerId = "providerID";

        //when
        userRegister.registerIfNeed(providerId, NORMAL);
        User findUser = userPersistencePort.findByProviderId(providerId);

        //then
        Assertions.assertThat(findUser).extracting("providerId", "providerType")
                .containsExactlyInAnyOrder(providerId, NORMAL);
    }
}