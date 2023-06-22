package kr.co.chooz.user.adapter;

import kr.co.chooz.user.repository.UserRepository;
import kr.co.chooz.user.domain.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findByUser(Long userId) {
        UserJpaEntity userJpaEntity = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        return userJpaEntity.toDomainUser();
    }
}
