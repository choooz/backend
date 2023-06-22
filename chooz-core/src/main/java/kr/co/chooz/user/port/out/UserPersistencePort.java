package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.User;

public interface UserPersistencePort {

    public boolean isUserExist(Long userId);

    public User findByUser(Long userId);
}
