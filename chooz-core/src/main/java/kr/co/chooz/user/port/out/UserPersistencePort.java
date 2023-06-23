package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.entitiy.User;

public interface UserPersistencePort {

    public boolean isUserExist(Long userId);

    public boolean isUserExistByProviderId(String providerId);

    public User findByUser(Long userId);

    public boolean register(User user);
}
