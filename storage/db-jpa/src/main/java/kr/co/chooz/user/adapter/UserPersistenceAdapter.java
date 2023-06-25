package kr.co.chooz.user.adapter;

import kr.co.chooz.user.repository.RegisterUserRepository;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final RegisterUserRepository userRepository;

    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean isUserExistByProviderId(String providerId) {
        return userRepository.existsByProviderId(providerId);
    }

    @Override
    public boolean existsByProviderId(String providerId) {
        return false;
    }

    @Override
    public User findByProviderId(String providerId) {
        return null;
    }

    @Override
    public User findByUserId(Long userId) {
        UserJpaEntity userJpaEntity = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        return userJpaEntity.toDomainUser();
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
