package kr.co.chooz.user.port.out;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.dto.AddUserCategory;
import kr.co.chooz.user.dto.AddUserInfo;

public interface UserStoreRepository {

    public User register(User user);

    public void addUserInfo(Long userId, AddUserInfo addUserInfo);

    public void addCategory(Long userId, AddUserCategory addUserCategory);
}
