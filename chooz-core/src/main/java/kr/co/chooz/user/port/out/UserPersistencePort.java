package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.entitiy.User;

import java.util.Optional;

public interface UserPersistencePort {

    public boolean isUserExist(Long userId);

    public boolean isUserExistByProviderId(String providerId);



    public User findByUserId(Long userId);

    public boolean register(User user);
}
