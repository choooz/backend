package kr.co.chooz.user.service;

import kr.co.chooz.token.domain.TokenGenerator;
import kr.co.chooz.user.domain.UserRegister;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.domain.validate.RegisterUserValidator;
import kr.co.chooz.user.dto.LoginRequest;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserPersistencePort userPersistencePort;
    private final UserRegister userRegister;
    private final RegisterUserValidator registerUserValidator;
    private final TokenGenerator tokenGenerator;



    public void loginUser(LoginRequest loginRequest) {
        if (!registerUserValidator.isDuplicatedUser(loginRequest.getProviderId())) {
            registerUser(loginRequest);
        }


    }

    public boolean registerUser(LoginRequest loginRequest) {
        User user = new User(loginRequest);
        userPersistencePort.register(user);
        return true;
    }




}