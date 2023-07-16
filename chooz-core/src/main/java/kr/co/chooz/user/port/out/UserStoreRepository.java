package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.entitiy.User;

public interface UserStoreRepository {

    public User register(User user);
}
