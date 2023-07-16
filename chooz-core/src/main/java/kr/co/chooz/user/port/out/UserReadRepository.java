package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.entitiy.User;

public interface UserReadRepository {

    public boolean existsByProviderId(String providerId);

    public User findByProviderId(String providerId);

    public User findByUserId(Long userId);
}
