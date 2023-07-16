package kr.co.chooz.user.repository;

import kr.co.chooz.support.RepositoryTest;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class UserReadRepositoryTest {

    @Autowired
    private UserReadRepository userReadRepository;

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
        userReadRepository.save(userJpaEntity);

        //when
        boolean actual = userReadRepository.existsByProviderId(providerId);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("ProviderId를 가진 유저를 조회한다.")
    @Test
    void findByProviderId() {
        //given
        String providerId = "1";
        User user = User.builder()
                .email("exam123@kakao.com")
                .providerId(providerId)
                .providerType(ProviderType.KAKAO)
                .build();

        UserJpaEntity userJpaEntity = UserJpaEntity.of(user);
        userReadRepository.save(userJpaEntity);

        //when
        Optional<UserJpaEntity> findUserEntity = userReadRepository.findByProviderId(providerId);

        //then
        assertAll(
                () -> assertThat(findUserEntity).isPresent(),
                () -> assertThat(findUserEntity.get()).isEqualTo(userJpaEntity)
        );
    }
}