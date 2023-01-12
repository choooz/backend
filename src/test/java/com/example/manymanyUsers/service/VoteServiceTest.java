package com.example.manymanyUsers.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.UpdateVoteRequest;
import com.example.manymanyUsers.vote.dto.VoteListData;
import com.example.manymanyUsers.vote.enums.*;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.service.VoteService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VoteServiceTest {

    @Autowired VoteService voteService;
    @Autowired UserService userService;
    @Autowired VoteRepository voteRepository;
    @Autowired UserRepository userRepository;

    @Test
    public void 투표생성_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "titleA",
                "titleB");

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();


        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTotalTitle(), createVoteRequest.getTitle());
        assertEquals(vote.getCategory(), createVoteRequest.getCategory());
        assertEquals(vote.getDetail(), createVoteRequest.getDetail());
        assertEquals(vote.getImageA(), createVoteRequest.getImageA());
        assertEquals(vote.getImageB(), createVoteRequest.getImageB());
        assertEquals(vote.getFilteredGender(), createVoteRequest.getFilteredGender());
        assertEquals(vote.getFilteredAge(), createVoteRequest.getFilteredAge());
        assertEquals(vote.getCategory(), createVoteRequest.getCategory());
    }

    @Test(expected = NotFoundException.class)
    public void 투표생성_실패_아이디를_가진_유저가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "titleA",
                "titleB");

        //then
        voteService.createVote(createVoteRequest,0L);

    }

    @Test
    public void 투표리스트_조회_성공() throws Exception {
        //given
        List<Vote> voteTestList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 유저 생성
            String name = "name" + i;
            String email = "email" + i;
            Providers provider;
            switch (i%3) {
                case 0:
                    provider = Providers.KAKAO;
                    email += "@kakao.com";
                    break;
                case 1 :
                    provider = Providers.NAVER;
                    email += "@naver.com";
                    break;
                case 2 :
                    provider = Providers.GOOGLE;
                    email += "@google.com";
                    break;
                default:
                    provider = Providers.KAKAO;
                    break;
            }

            SignUpRequest signUpRequest = new SignUpRequest(name, email, "", provider, "");
            Long userid;
            try {
                userid = userService.registerUser(signUpRequest);

                // 투표 생성
                String voteTitle = "title " + i;
                String imageA = "https://mblogthumb-phinf.pstatic.net/MjAxODA0MjRfNDYg/MDAxNTI0NTQ5MDc3MDU1.iF25cKVnG3Ae8BkD20IIoB5U1vlOcN3kXWt7XjU0jR8g.1yRmaxjdNtOgATk4gnZ_cr4WnoBauqUgoE0yg0q4QWog.JPEG.beelike1115/image_5370526891524549066762.jpg?type=w800";
                String imageB = "https://mblogthumb-phinf.pstatic.net/MjAxODA0MjRfNDYg/MDAxNTI0NTQ5MDc3MDU1.iF25cKVnG3Ae8BkD20IIoB5U1vlOcN3kXWt7XjU0jR8g.1yRmaxjdNtOgATk4gnZ_cr4WnoBauqUgoE0yg0q4QWog.JPEG.beelike1115/image_5370526891524549066762.jpg?type=w800";
                String detail = "디테일 텍스트" + i;
                Gender filteredGender = Gender.NULL;
                switch (i%2){
                    case 1  : filteredGender = Gender.FEMALE;
                        break;
                    case 2 : filteredGender = Gender.MALE;
                        break;
                    default:
                        break;
                }
                Age filteredAge = Age.NULL;
                Category category = Category.NULL;
                switch (i%5){
                    case 1: category = Category.CARRIER;
                        break;
                    case 2: category = Category.FOODS;
                        break;
                    case 3: category = Category.LOVE;
                        break;
                    case 4: category = Category.FASHION;
                        break;
                    case 5: category = Category.INTEREST;
                    default: break;
                }
                MBTI filteredMbti = MBTI.ENFJ;
                switch (i % 4) {
                    case 1 : filteredMbti = MBTI.ENFP;
                        break;
                    case 2 : filteredMbti = MBTI.ENTJ;
                        break;
                    case 3 : filteredMbti = MBTI.INFP;
                        break;
                    case 4 : filteredMbti = MBTI.ISFJ;
                    default: break;
                }
                String titleA = "titleA" + i;
                String titleB = "titleB" + i;
                CreateVoteRequest createVoteRequest = new CreateVoteRequest(voteTitle, imageA, imageB, detail, filteredGender, filteredAge, category, filteredMbti, titleA, titleB);
                Vote vote = voteService.createVote(createVoteRequest, userid);
                voteTestList.add(vote);
            } catch (Exception e) {
                System.out.println("e = " + e);
            }
        }
        //when
        Slice<VoteListData> voteSlice = voteService.getVoteList(SortBy.ByTime, 0, 10);
        List<VoteListData> voteResultList = voteSlice.getContent();
        //then
        voteTestList.sort((v1,v2)-> {
            if (v1.getCreatedDate().isBefore(v2.getCreatedDate())) {
                return 1;
            } else if (v1.getCreatedDate().isAfter(v2.getCreatedDate())) {
                return -1;
            } else {
                return 0;
            }
        });
        int i =0;
        for (VoteListData voteListData : voteResultList) {
            System.out.println("voteListData.getVoteId() = " + voteListData.getVoteId());
            System.out.println("voteTestList.get(i).getId() = " + voteTestList.get(i).getId());
            System.out.println("i = " + i);
            assertEquals(voteListData.getVoteId(),voteTestList.get(i).getId());
            i++;
        }
    }

    @Test
    public void 투표수정_성공() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "titleA",
                "titleB");

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();
        System.out.println("수정 전 타이틀: " + vote.getTotalTitle());
        System.out.println("수정 전 이미지: " + vote.getImageA());
        System.out.println("수정 전 이미지: " + vote.getImageB());
        System.out.println("수정 전 타이틀A " + vote.getTitleA());
        System.out.println("수정 전 타이틀B " + vote.getTitleB());

        //when
        UpdateVoteRequest updateVoteRequest = new UpdateVoteRequest(
                vote.getId(),
                "title, titleA 만 바꾸겠습니다",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "title, titleA 만 바꾸겠습니다",
                "titleB");

        voteService.updateVote(updateVoteRequest, userId);

        //then

        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTotalTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getImageA(), updateVoteRequest.getImageA());
        assertEquals(vote.getImageB(), updateVoteRequest.getImageB());
        assertEquals(vote.getFilteredGender(), updateVoteRequest.getFilteredGender());
        assertEquals(vote.getFilteredAge(), updateVoteRequest.getFilteredAge());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

        System.out.println("수정 후 타이틀: " + vote.getTotalTitle());
        System.out.println("수정 후 이미지: " + vote.getImageA());
        System.out.println("수정 후 이미지: " + vote.getImageB());
        System.out.println("수정 후 타이틀A " + vote.getTitleA());
        System.out.println("수정 후 타이틀B " + vote.getTitleB());
    }

    @Test(expected = NotFoundException.class)
    public void 투표수정_실패_아이디를_가진_유저가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "titleA",
                "titleB");

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        UpdateVoteRequest updateVoteRequest = new UpdateVoteRequest(
                vote.getId(),
                "title, titleA 만 바꾸겠습니다",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "title, titleA 만 바꾸겠습니다",
                "titleB");

        voteService.updateVote(updateVoteRequest, 100L);

        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTotalTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getImageA(), updateVoteRequest.getImageA());
        assertEquals(vote.getImageB(), updateVoteRequest.getImageB());
        assertEquals(vote.getFilteredGender(), updateVoteRequest.getFilteredGender());
        assertEquals(vote.getFilteredAge(), updateVoteRequest.getFilteredAge());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

    }

    @Test(expected = NotFoundException.class)
    public void 투표수정_실패_아이디를_가진_투표가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "titleA",
                "titleB");

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        UpdateVoteRequest updateVoteRequest = new UpdateVoteRequest(
                100L,
                "title, titleA 만 바꾸겠습니다",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL,
                MBTI.ENFJ,
                "title, titleA 만 바꾸겠습니다",
                "titleB");

        voteService.updateVote(updateVoteRequest, userId);

        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTotalTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getImageA(), updateVoteRequest.getImageA());
        assertEquals(vote.getImageB(), updateVoteRequest.getImageB());
        assertEquals(vote.getFilteredGender(), updateVoteRequest.getFilteredGender());
        assertEquals(vote.getFilteredAge(), updateVoteRequest.getFilteredAge());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

    }

//    @Test(expected = NoSuchElementException.class)
//    public void 투표삭제_성공() throws Exception {
//
//        //given
//        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.NAVER, "providerId");
//        Long userId = userService.registerUser(request);
//
//        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
//                userId,
//                "투표 제목",
//                "imageA",
//                "imageB",
//                "detailText",
//                Gender.NULL,
//                Age.NULL,
//                Category.NULL,
//                MBTI.ENFJ,
//                "titleA",
//                "titleB");
//
//        voteService.createVote(createVoteRequest);
//
//        Optional<User> userRepositoryByProviderId = userRepository.findById(createVoteRequest.getUserId());
//        User user = userRepositoryByProviderId.get();
//
//        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
//        Vote vote = byProviderId.get();
//
//        //when
//
//        voteService.deleteVote(vote.getId());
//
//        //then
//
//        Optional<Vote> removedVote = voteRepository.findById(vote.getId());
//
//        System.out.println("removedVote.Id(): " + removedVote.getClass());
//        Vote removedVote2 = removedVote.get();  //투표를 찾을 수 없음 (NoSuchElementException)
//
//    }
}
