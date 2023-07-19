package kr.co.chooz.user.repository;

import kr.co.chooz.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReadRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByProviderId(String providerId);

    Optional<UserJpaEntity> findByProviderId(String providerId);
}