package com.example.manymanyUsers.service;

import com.example.manymanyUsers.exception.user.UserIllegalStateException;
import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.user.domain.CategoryRespository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.AddInfoRequest;
import com.example.manymanyUsers.user.dto.AddInterestCategoryRequest;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CategoryRespository categoryRespository;

    @InjectMocks
    private UserService mockUserService;



    @Test
    public void 유저정보추가_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInfoRequest addInfoRequest = new AddInfoRequest();

        addInfoRequest.setAge(26);
        addInfoRequest.setGender(Gender.MALE);
        addInfoRequest.setMbti(MBTI.INFJ);

        //when
        userService.addUserInfo(addInfoRequest,userId);

        //then
        Optional<User> byId = userRepository.findById(userId);
        User changedUser = byId.get();

        assertEquals(changedUser.getAge(), addInfoRequest.getAge());
        assertEquals(changedUser.getGender(), addInfoRequest.getGender());
        assertEquals(changedUser.getMbti(), addInfoRequest.getMbti());

    }

    @Test(expected = NotFoundException.class)
    public void 유저정보추가_실패() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInfoRequest addInfoRequest = new AddInfoRequest();

        addInfoRequest.setAge(26);
        addInfoRequest.setGender(Gender.MALE);
        addInfoRequest.setMbti(MBTI.INFJ);

        //when
        userService.addUserInfo(addInfoRequest, 0L);

        //then

    }

    @Test
    public void 유저관심_카테고리_한개_추가_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInterestCategoryRequest addInterestCategoryRequest = new AddInterestCategoryRequest();

        List<Category> categoryLists = addInterestCategoryRequest.getCategoryLists();
        categoryLists.add(Category.LOVE);

        //when
        userService.addInterestCategory(categoryLists,userId);

        Optional<User> byId = userRepository.findById(userId);
        User result = byId.get();
        List<CategoryEntity> resultList = result.getCategoryLists();

        //then
        CategoryEntity category1 = resultList.get(0);
        assertEquals(category1.getCategory(), Category.LOVE);
    }


    @Test
    public void 유저관심_카테고리_여러개_추가_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInterestCategoryRequest addInterestCategoryRequest = new AddInterestCategoryRequest();

        List<Category> categoryLists = addInterestCategoryRequest.getCategoryLists();
        categoryLists.add(Category.LOVE);
        categoryLists.add(Category.FASHION);

        //when
        userService.addInterestCategory(categoryLists, userId);

        Optional<User> byId = userRepository.findById(userId);
        User result = byId.get();
        List<CategoryEntity> resultList = result.getCategoryLists();

        //then
        CategoryEntity category1 = resultList.get(0);
        CategoryEntity category2 = resultList.get(1);
        assertEquals(category1.getCategory(), Category.LOVE);
        assertEquals(category2.getCategory(), Category.FASHION);
    }

    @Test(expected = NotFoundException.class)
    public void 유저관심카테고리추가_실패_유저를찾을수없음() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInterestCategoryRequest addInterestCategoryRequest = new AddInterestCategoryRequest();

        List<Category> categoryLists = addInterestCategoryRequest.getCategoryLists();
        categoryLists.add(Category.LOVE);
        categoryLists.add(Category.FASHION);

        //when
        userService.addInterestCategory(categoryLists, 0L);

        //then

    }

    @Test
    public void 프로필수정_성공() throws NotFoundException {
        // given
        Long userId = 1L;
        String nickname = "updatedNickname";
        String image = "updatedImage";
        MBTI originalMbti = MBTI.INFP;
        MBTI updatedMbti = MBTI.ENFJ;
        LocalDateTime modifiedMbtiDate = LocalDateTime.now().minusMonths(2); // 두 달 전에 MBTI를 수정함
        List<Category> categoryList = Arrays.asList(Category.LOVE, Category.FOODS);

        CategoryEntity categoryEntity1 = new CategoryEntity(2L, Category.CAREER);
        CategoryEntity categoryEntity2 = new CategoryEntity(3L, Category.FOODS);

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity1);
        categoryEntityList.add(categoryEntity2);


        User user = User.builder()
                .id(userId)
                .nickname("originalNickname")
                .imageUrl("originalImage")
                .mbti(originalMbti)
                .categoryLists(new ArrayList<>())
                .build();


        Mockito.when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));


        user.setModifiedMBTIDate(modifiedMbtiDate);


        // when
        mockUserService.updateUser(userId, nickname, image, updatedMbti, categoryList);

        //then
        assertEquals(nickname, user.getNickname());
        assertEquals(image, user.getImageUrl());
        assertEquals(updatedMbti, user.getMbti());
        assertEquals(categoryList, user.getCategoryLists().stream().map(CategoryEntity::toCategory).collect(Collectors.toList()));
    }


    @Test
    public void 프로필수정_실패() throws NotFoundException {
        // given
        Long userId = 1L;
        String nickname = "updatedNickname";
        String image = "updatedImage";
        MBTI originalMbti = MBTI.INFP;
        MBTI updatedMbti = MBTI.ENFJ;
        LocalDateTime modifiedMbtiDate = LocalDateTime.now().minusMonths(1); // 두 달 전에 MBTI를 수정함
        List<Category> categoryList = Arrays.asList(Category.LOVE, Category.FOODS);

        CategoryEntity categoryEntity1 = new CategoryEntity(2L, Category.CAREER);
        CategoryEntity categoryEntity2 = new CategoryEntity(3L, Category.FOODS);

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity1);
        categoryEntityList.add(categoryEntity2);


        User user = User.builder()
                .id(userId)
                .nickname("originalNickname")
                .imageUrl("originalImage")
                .mbti(originalMbti)
                .categoryLists(new ArrayList<>())
                .build();


        Mockito.when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));


        user.setModifiedMBTIDate(modifiedMbtiDate);

        //when, then
        assertThrows(UserIllegalStateException.class, () -> {
            mockUserService.updateUser(userId, nickname, image, updatedMbti, categoryList);
        });
    }


}
