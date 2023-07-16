package kr.co.chooz.user.adapter;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.port.out.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreAdapter implements UserStoreRepository {

    private final kr.co.chooz.user.repository.UserStoreRepository userRepository;

    @Override
    public User register(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.of(user);
        UserJpaEntity save = userRepository.save(userJpaEntity);
        return save.toDomainUser();
    }
}
