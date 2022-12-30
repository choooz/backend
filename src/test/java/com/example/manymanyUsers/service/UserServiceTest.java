package com.example.manymanyUsers.service;

import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.AddInfoRequest;
import com.example.manymanyUsers.user.dto.AddInterestCategoryRequest;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;


    @Test
    public void 유저정보추가_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        AddInfoRequest addInfoRequest = new AddInfoRequest();

        addInfoRequest.setUserId(userId);
        addInfoRequest.setAge(26);
        addInfoRequest.setGender(Gender.MALE);
        addInfoRequest.setMbti(MBTI.INFJ);

        //when
        userService.addUserInfo(addInfoRequest);

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
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        AddInfoRequest addInfoRequest = new AddInfoRequest();

        addInfoRequest.setUserId(0L);
        addInfoRequest.setAge(26);
        addInfoRequest.setGender(Gender.MALE);
        addInfoRequest.setMbti(MBTI.INFJ);

        //when
        userService.addUserInfo(addInfoRequest);

        //then

    }

    @Test
    public void 유저관심카테고리추가_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        AddInterestCategoryRequest addInterestCategoryRequest = new AddInterestCategoryRequest();
        addInterestCategoryRequest.setUserId(userId);

        List<Category> categoryLists = addInterestCategoryRequest.getCategoryLists();
        categoryLists.add(Category.LOVE);
        categoryLists.add(Category.FASHION);

        //when
        userService.addInterestCategory(addInterestCategoryRequest);

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
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        AddInterestCategoryRequest addInterestCategoryRequest = new AddInterestCategoryRequest();
        addInterestCategoryRequest.setUserId(0L);

        List<Category> categoryLists = addInterestCategoryRequest.getCategoryLists();
        categoryLists.add(Category.LOVE);
        categoryLists.add(Category.FASHION);

        //when
        userService.addInterestCategory(addInterestCategoryRequest);

        //then

    }
}
