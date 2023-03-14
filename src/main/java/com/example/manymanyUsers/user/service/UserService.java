package com.example.manymanyUsers.user.service;

import com.example.manymanyUsers.exception.user.UserIllegalStateException;
import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.user.domain.CategoryRespository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.AddInfoRequest;
import com.example.manymanyUsers.user.dto.GetUserNickNameRequest;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.repository.BookmarkRepository;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRespository categoryRespository;
    private final VoteRepository voteRepository;
    private final VoteResultRepository voteResultRepository;

    private final BookmarkRepository bookmarkRepository;


    public Long registerUser(SignUpRequest signUpRequestDto) throws Exception{
        if (userRepository.existsByProviderId(signUpRequestDto.getProviderId())) {
            throw new Exception("중복된 유저가 존재합니다.");
        }

        User user = new User();
        user.setNickname(signUpRequestDto.getName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(signUpRequestDto.getPassword());

        user.setMbti(MBTI.NULL);
        user.setGender(Gender.NULL);
        user.setAge(0);

        User result = userRepository.save(user);
        return result.getId();
    }

    public void addUserInfo(AddInfoRequest addInfoRequest, Long userId) throws NotFoundException{
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
        }

        User user = byId.get();

        if(user.getAge().equals(0) && user.getGender().equals(Gender.NULL) && user.getMbti().equals(MBTI.NULL) ){
            // 새로운 유저일때 닉네임 랜덤으로 생성
            GetUserNickNameRequest nickNameRequest = getUserNickName();

            String[] nickNameRequestWords = nickNameRequest.getWords();
            user.setNickname(nickNameRequestWords[0]);
        }

        user.setAge(addInfoRequest.getAge());
        user.setGender(addInfoRequest.getGender());
        user.setMbti(addInfoRequest.getMbti());
        //MBTI 수정 DATE 계산하기 위한 LocalDate
        user.setModifiedMBTIDate(LocalDateTime.now());


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
    public void addInterestCategory(List<Category> lists, Long userId) throws NotFoundException{
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
        }

        User user = byId.get();
        List<CategoryEntity> categoryLists = user.getCategoryLists();
        for (Category list : lists) {
            CategoryEntity category = new CategoryEntity();
            category.setCategory(list);
            categoryRespository.save(category);

            categoryLists.add(category);
        }

        userRepository.save(user);
    }

    public Map<String, Long> getMyPageCount(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Map<String, Long> map = new HashMap<>();

        Long createdVoteCount = 0L;
        Long participatedVoteCount = 0L;
        Long bookmarkedVoteCount = 0L;


        createdVoteCount = voteRepository.countVoteByPostedUser(findUser);
        participatedVoteCount = voteResultRepository.countByVotedUser(findUser);
        bookmarkedVoteCount = bookmarkRepository.countByUser(findUser);

        map.put("CREATED_VOTE", createdVoteCount);
        map.put("PARTICIPATED_VOTE", participatedVoteCount);
        map.put("BOOKMARKED_VOTE", bookmarkedVoteCount);

        return map;
    }


    public User updateUser(Long userId, String nickname, String image, MBTI mbti, List<Category> categoryList) throws NotFoundException {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        //MBTI 로직
        if (!Objects.equals(mbti, findUser.getMbti())) {
            LocalDateTime now = LocalDateTime.now();
            if (findUser.getModifiedMBTIDate() != null && now.isBefore(findUser.getModifiedMBTIDate().plusMonths(2))) { // 현재 시간이 특정 사용자의 마지막 MBTI 수정 일자보다 2개월 이내인지를 확인
                throw new UserIllegalStateException();
            } else {
                findUser.updateMbti(mbti, now);
            }
        }

        findUser.updateProfile(nickname,image);

        //CategoryEntity -> Category 로 변환해서 비교해주기 위해
        List<Category> toCategoryList = findUser.getCategoryLists().stream()
                .map(CategoryEntity::toCategory)
                .collect(Collectors.toList());

        boolean isEqual = toCategoryList.stream().collect(Collectors.toSet())
                .equals(categoryList.stream().collect(Collectors.toSet()));




        if (!isEqual) {
            List<CategoryEntity> findUserCategoryLists = findUser.getCategoryLists();
            findUser.clearCategoryList();
            for (Category list : categoryList) {
                CategoryEntity category = new CategoryEntity();
                category.setCategory(list);
                categoryRespository.save(category);

                findUserCategoryLists.add(category);
            }
            userRepository.save(findUser);
        }


        return findUser;
    }

}
