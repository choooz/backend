package kr.co.chooz.user.repository;

import kr.co.chooz.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStoreRepository extends JpaRepository<UserJpaEntity, Long> { }