package kr.co.chooz.user.adapter;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.dto.AddUserInfo;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.port.out.UserStoreRepository;
import kr.co.chooz.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreAdapter implements UserStoreRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User register(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.of(user);
        UserJpaEntity save = userJpaRepository.save(userJpaEntity);
        return save.toDomainUser();
    }

    @Override
    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(RuntimeException::new);
        userJpaEntity.addInfo(addUserInfo);
    }
}
