package kr.co.chooz.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class ManageService {

//    private final UserPersistencePort userPersistencePort;

//    public void addUserInfo(AddInfoRequest addInfoRequest, Long userId) throws NotFoundException{

//        Optional<User> byId = userRepository.findById(userId);
//        if (byId.isEmpty()) {
//            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
//        }
//
//        User user = byId.get();
//
//        if(user.getAge().equals(0) && user.getGender().equals(Gender.NULL) && user.getMbti().equals(MBTI.NULL) ){
//            // 새로운 유저일때 닉네임 랜덤으로 생성
//            GetUserNickNameRequest nickNameRequest = getUserNickName();
//
//            String[] nickNameRequestWords = nickNameRequest.getWords();
//            user.setNickname(nickNameRequestWords[0]);
//        }
//
//        user.setAge(addInfoRequest.getAge());
//        user.setGender(addInfoRequest.getGender());
//        user.setMbti(addInfoRequest.getMbti());
//        //MBTI 수정 DATE 계산하기 위한 LocalDate
//        user.setModifiedMBTIDate(LocalDateTime.now());
//
//
//        userRepository.save(user);

//    }

    /**
     * 외부 api 호출해서 랜덤닉네임을 생성
     //     * @return GetUserNickNameRequest
     */
//    public GetUserNickNameRequest getUserNickName() {
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://nickname.hwanmoo.kr/")
//                .queryParam("format", "json")
//                .queryParam("count", 1)
//                .queryParam("max_length", 8)
//                .encode()
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<GetUserNickNameRequest> result = restTemplate.getForEntity(uri, GetUserNickNameRequest.class);
//
//        return result.getBody();
//    }


    /**
     * 유저 정보 기입 이후에 호출되며 유저가 관심이 있는 카테고리를 추가 해주는 메서드
     */
//    public void addInterestCategory(List<Category> lists, Long userId) throws NotFoundException{
//        Optional<User> byId = userRepository.findById(userId);
//        if (byId.isEmpty()) {
//            throw new NotFoundException("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
//        }
//
//        User user = byId.get();
//        List<CategoryEntity> categoryLists = user.getCategoryLists();
//        for (Category list : lists) {
//            CategoryEntity category = new CategoryEntity();
//            category.setCategory(list);
//            categoryRespository.save(category);
//
//            categoryLists.add(category);
//        }
//
//        userRepository.save(user);
//    }

}
