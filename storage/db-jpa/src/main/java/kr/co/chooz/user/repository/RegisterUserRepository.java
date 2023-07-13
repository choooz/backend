package kr.co.chooz.user.repository;

import kr.co.chooz.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterUserRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByProviderId(String providerId);

    Optional<UserJpaEntity> findByProviderId(String providerId);
}