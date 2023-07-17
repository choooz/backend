package kr.co.chooz.user.adapter;

import kr.co.chooz.support.AdapterTest;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.dto.AddUserInfo;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static kr.co.chooz.user.domain.entitiy.ProviderType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@AdapterTest
class UserStoreAdapterTest {

    @Autowired
    private UserStoreAdapter userStoreAdapter;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @DisplayName("기존 유저의 정보를 추가한다.")
    @Test
    void addUserInfo() {
        //given
        User registeredUser = registerUser();

        MbtiType mbti = MbtiType.ENFJ;
        Integer age = 20;
        GenderType gender = GenderType.MALE;

        AddUserInfo addUserInfo = new AddUserInfo(mbti, age, gender);
        Long userId = registeredUser.getId();

        //when
        userStoreAdapter.addUserInfo(userId, addUserInfo);
        Optional<UserJpaEntity> findUserEntity = userJpaRepository.findById(userId);

        //then
        assertAll(() -> assertThat(findUserEntity).isPresent(), () -> assertThat(findUserEntity.get()).extracting("mbti", "age", "gender").contains(mbti, age, gender));

    }

    private User registerUser() {
        User user = User.builder().providerType(NORMAL).providerId("providerId").build();
        return userStoreAdapter.register(user);
    }

}