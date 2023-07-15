package kr.co.chooz.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = RegisterUserRepository.class)
class RegisterUserRepositoryTest {

    @Autowired
    private RegisterUserRepository registerUserRepository;

    @DisplayName("ProviderId를 가진 유저가 존재하는지 확인한다.")
    @Test
    void existsByProviderId() {
        //given
//        User("")

//        UserJpaEntity.of()
        //when

        //then

    }

}