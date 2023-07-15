package kr.co.chooz.user.repository;

import kr.co.chooz.TestConfiguration;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
@TestPropertySource("classpath:application-jpa-test.yml")
class RegisterUserRepositoryTest {

    @Autowired
    private RegisterUserRepository registerUserRepository;

    @DisplayName("ProviderId를 가진 유저가 존재하는지 확인한다.")
    @Test
    void existsByProviderId() {
        //given
        String providerId = "1";
        User user = User.builder()
                .email("exam123@kakao.com")
                .providerId(providerId)
                .providerType(ProviderType.KAKAO)
                .build();

        UserJpaEntity userJpaEntity = UserJpaEntity.of(user);

        registerUserRepository.save(userJpaEntity);
        //when
        boolean actual = registerUserRepository.existsByProviderId(providerId);

        //then
        assertThat(actual).isTrue();
    }
}