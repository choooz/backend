package com.example.manymanyUsers.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);

    Optional<User> findByProviderId(String providerId);

    Boolean existsByProviderId(String providerId);
}
