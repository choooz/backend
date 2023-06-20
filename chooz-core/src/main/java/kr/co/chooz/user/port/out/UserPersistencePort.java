package kr.co.chooz.user.port.out;

public interface UserPersistencePort {

    public boolean isUserExist(Long userId);
}
