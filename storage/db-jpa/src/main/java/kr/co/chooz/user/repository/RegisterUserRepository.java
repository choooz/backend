package kr.co.chooz.user.repository;

import kr.co.chooz.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterUserRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByProviderId(String providerId);

    UserJpaEntity findByProviderId(String providerId);
}