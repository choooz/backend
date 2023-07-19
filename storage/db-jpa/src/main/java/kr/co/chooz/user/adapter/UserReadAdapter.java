package kr.co.chooz.user.adapter;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.port.out.UserReadRepository;
import kr.co.chooz.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadAdapter implements UserReadRepository {

    private final UserJpaRepository userReadRepository;


    @Override
    public boolean existsByProviderId(String providerId) {
        return userReadRepository.existsByProviderId(providerId);
    }

    @Override
    public User findByProviderId(String providerId) {
        UserJpaEntity userJpaEntity = userReadRepository.findByProviderId(providerId).orElseThrow(RuntimeException::new);
        return userJpaEntity.toDomainUser();
    }

    @Override
    public User findByUserId(Long userId) {
        UserJpaEntity userJpaEntity = userReadRepository.findById(userId).orElseThrow(RuntimeException::new);
        return userJpaEntity.toDomainUser();
    }
}
