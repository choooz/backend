package com.example.manymanyUsers.user.service;

import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.user.dto.AddInfoRequest;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.AddInterestCategoryRequest;
import com.example.manymanyUsers.user.dto.GetUserNickNameRequest;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.vote.enums.Category;
import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public Long registerUser(SignUpRequest signUpRequestDto) throws Exception{
        if (userRepository.existsByProviderId(signUpRequestDto.getProviderId())) {
            throw new Exception("중복된 유저가 존재합니다.");
        }

        User user = new User();
        user.setNickname(signUpRequestDto.getName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(signUpRequestDto.getPassword());

        User result = userRepository.save(user);
        return result.getId();
    }

    public void addUserInfo(AddInfoRequest addInfoRequest) throws NotFoundException{
        Optional<User> byId = userRepository.findById(addInfoRequest.getUserId());
        if (byId.isEmpty()) {
            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
        }

        User user = byId.get();

        user.setAge(addInfoRequest.getAge());
        user.setGender(addInfoRequest.getGender());
        user.setMbti(addInfoRequest.getMbti());

        GetUserNickNameRequest nickNameRequest = getUserNickName();

        String[] nickNameRequestWords = nickNameRequest.getWords();
        user.setNickname(nickNameRequestWords[0]);
        userRepository.save(user);
    }


    /**
     * 외부 api 호출해서 랜덤닉네임을 생성
     * @return GetUserNickNameRequest
     */
    public GetUserNickNameRequest getUserNickName() {
        URI uri = UriComponentsBuilder
                .fromUriString("https://nickname.hwanmoo.kr/")
                .queryParam("format", "json")
                .queryParam("count", 1)
                .queryParam("max_length", 8)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GetUserNickNameRequest> result = restTemplate.getForEntity(uri, GetUserNickNameRequest.class);

        return result.getBody();
    }


    /**
     * 유저 정보 기입 이후에 호출되며 유저가 관심이 있는 카테고리를 추가 해주는 메서드
     */
    public void addInterestCategory(AddInterestCategoryRequest addInterestCategoryRequest) throws NotFoundException{
        Optional<User> byId = userRepository.findById(addInterestCategoryRequest.getUserId());
        if (byId.isEmpty()) {
            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
        }

        User user = byId.get();
        List<CategoryEntity> categoryLists = user.getCategoryLists();
        List<Category> lists = addInterestCategoryRequest.getCategoryLists();
        for (Category list : lists) {
            CategoryEntity category = new CategoryEntity();
            category.setCategoryList(list);
            categoryLists.add(category);
        }

        userRepository.save(user);
    }

}
