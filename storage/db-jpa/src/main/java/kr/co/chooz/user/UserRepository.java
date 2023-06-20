package kr.co.chooz.user;

import kr.co.chooz.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByProviderId(String providerId);

    void register(UserJpaEntity user);

    UserJpaEntity findByProviderId(String providerId);
}