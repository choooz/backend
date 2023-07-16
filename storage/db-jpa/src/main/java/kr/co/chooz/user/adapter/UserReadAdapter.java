package kr.co.chooz.user.adapter;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadAdapter implements kr.co.chooz.user.port.out.UserReadRepository {

    private final UserReadRepository userReadRepository;


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
