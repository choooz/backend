package com.example.manymanyUsers.service;

import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.exception.vote.AlreadyUserDoVoteException;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
import com.example.manymanyUsers.statistics.service.StatisticsService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.AddInfoRequest;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.vote.enums.*;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import com.example.manymanyUsers.vote.service.VoteService;
import org.junit.Test;
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
    @Autowired StatisticsService statisticsService;
    @Autowired VoteRepository voteRepository;
    @Autowired UserRepository userRepository;

    @Autowired VoteResultRepository voteResultRepository;

    @Test
    public void 투표생성_성공() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        //when

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();


        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();


        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTitle(), createVoteRequest.getTitle());
        assertEquals(vote.getImageA(), createVoteRequest.getImageA());
        assertEquals(vote.getImageB(), createVoteRequest.getImageB());
        assertEquals(vote.getTitleA(), createVoteRequest.getTitleA());
        assertEquals(vote.getTitleB(), createVoteRequest.getTitleB());
    }

    @Test(expected = UserNotFoundException.class)
    public void 투표생성_실패_아이디를_가진_유저가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

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
                Category category = Category.ETC;
                switch (i%5){
                    case 1: category = Category.CAREER;
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
                CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                        .title("투표 제목")
                        .imageA("imageA")
                        .imageB("imageB")
                        .titleA("titleA")
                        .titleB("titleB")
                        .build();
                Long voteId = voteService.createVote(createVoteRequest, userid);
                Optional<Vote> byId = voteRepository.findById(voteId);
                Vote vote = byId.get();
                voteTestList.add(vote);
            } catch (Exception e) {
                System.out.println("e = " + e);
            }
        }
        //when
        Slice<VoteListData> voteSlice = voteService.getVoteList(SortBy.ByTime, 0, 10, null);
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
            System.out.println("i = " + i);
            System.out.println("voteListData.getVoteId() = " + voteListData.getVoteId());
            System.out.println("voteTestList = " + voteTestList);
            System.out.println("voteTestList.get(i).getId() = " + voteTestList.get(i).getId());
            assertEquals(voteListData.getVoteId(),voteTestList.get(i).getId());
            i++;
        }
    }

    @Test
    public void 투표수정_성공() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();
        System.out.println("수정 전 타이틀: " + vote.getTitle());
        System.out.println("수정 전 이미지: " + vote.getImageA());
        System.out.println("수정 전 이미지: " + vote.getImageB());
        System.out.println("수정 전 타이틀A " + vote.getTitleA());
        System.out.println("수정 전 타이틀B " + vote.getTitleB());

        //when
        UpdateVoteRequest updateVoteRequest = UpdateVoteRequest.builder()
                .title("수정 후 타이틀")
                .detail("수정 후 디테일")
                .category(Category.ETC)
                .titleA("수정 후 타이틀A")
                .titleB("수정 후 타이틀B")
                .build();



        voteService.updateVote(updateVoteRequest, userId, vote.getId());

        //then

        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

        System.out.println("수정 후 타이틀: " + vote.getTitle());
        System.out.println("수정 후 이미지: " + vote.getImageA());
        System.out.println("수정 후 이미지: " + vote.getImageB());
        System.out.println("수정 후 타이틀A " + vote.getTitleA());
        System.out.println("수정 후 타이틀B " + vote.getTitleB());
    }

    @Test(expected = UserNotFoundException.class)
    public void 투표수정_실패_아이디를_가진_유저가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        UpdateVoteRequest updateVoteRequest = UpdateVoteRequest.builder()
                .title("수정 후 타이틀")
                .detail("수정 후 디테일")
                .category(Category.ETC)
                .titleA("수정 후 타이틀A")
                .titleB("수정 후 타이틀B")
                .build();

        voteService.updateVote(updateVoteRequest, 100L, vote.getId());

        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

    }

    @Test(expected = VoteNotFoundException.class)
    public void 투표수정_실패_아이디를_가진_투표가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        UpdateVoteRequest updateVoteRequest = UpdateVoteRequest.builder()
                .title("수정 후 타이틀")
                .detail("수정 후 디테일")
                .category(Category.ETC)
                .titleA("수정 후 타이틀A")
                .titleB("수정 후 타이틀B")
                .build();

        voteService.updateVote(updateVoteRequest, userId, 0L);

        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTitle(), updateVoteRequest.getTitle());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());
        assertEquals(vote.getDetail(), updateVoteRequest.getDetail());
        assertEquals(vote.getCategory(), updateVoteRequest.getCategory());

    }

    @Test(expected = NoSuchElementException.class)
    public void 투표삭제_성공() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();


        //when

        voteService.deleteVote(vote.getId(), userId);

        //then

        Optional<Vote> removedVote = voteRepository.findById(vote.getId());

        System.out.println("removedVote.Id(): " + removedVote.getClass());
        Vote removedVote2 = removedVote.get();  //투표를 찾을 수 없음 (NoSuchElementException)

    }

    @Test(expected = VoteNotFoundException.class)
    public void 투표삭제_실패_아이디를_가진_투표가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();


        //when

        voteService.deleteVote(100L, userId);

        //then

        Optional<Vote> removedVote = voteRepository.findById(vote.getId());

        System.out.println("removedVote.Id(): " + removedVote.getClass());
        Vote removedVote2 = removedVote.get();  //투표를 찾을 수 없음 (NoSuchElementException)

    }

    @Test(expected = UserNotFoundException.class)
    public void 투표삭제_실패_아이디를_가진_유저가_없음() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();


        //when

        voteService.deleteVote(vote.getId(), 100L);

        //then

        Optional<Vote> removedVote = voteRepository.findById(vote.getId());

        System.out.println("removedVote.Id(): " + removedVote.getClass());
        Vote removedVote2 = removedVote.get();  //투표를 찾을 수 없음 (NoSuchElementException)

    }

    @Test
    public void 투표단건조회_성공() throws Exception{
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        AddInfoRequest addInfoRequest = new AddInfoRequest();

        addInfoRequest.setAge(26);
        addInfoRequest.setGender(Gender.MALE);
        addInfoRequest.setMbti(MBTI.INFJ);

        userService.addUserInfo(addInfoRequest,userId);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userById = userRepository.findById(userId);
        User user = userById.get();

        Optional<Vote> voteById = voteRepository.findByPostedUser(user);
        Vote vote = voteById.get();

        //when
        Vote getVote = voteService.getVote(vote.getId());


        //then
        assertEquals(vote,getVote);
        assertEquals(user,vote.getPostedUser());
        assertEquals(user.classifyAge(user.getAge()),Age.twenties);
    }

    @Test
    public void 투표참여_성공() throws Exception {

        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        DoVoteRequest doVoteRequest = new DoVoteRequest(Choice.A);
        voteService.doVote(doVoteRequest.converter(userId, vote.getId()));

        //then
        VoteResult voteResult = voteResultRepository.findByVote(vote);
        assertEquals(vote.getId(), voteResult.getVote().getId());
        assertEquals(user.getId(), voteResult.getVotedUser().getId());
        assertEquals(Choice.A, voteResult.getChoice());

    }

    @Test(expected = UserNotFoundException.class)
    public void 투표참여_실패_아이디를_가진_유저가_없음() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        DoVoteRequest doVoteRequest = new DoVoteRequest(Choice.A);
        voteService.doVote(doVoteRequest.converter(100L, vote.getId()));

        //then
        VoteResult voteResult = voteResultRepository.findByVote(vote);
        assertEquals(vote.getId(), voteResult.getVote().getId());
        assertEquals(user.getId(), voteResult.getVotedUser().getId());
        assertEquals(Choice.A, voteResult.getChoice());
    }

    @Test(expected = VoteNotFoundException.class)
    public void 투표참여_실패_아이디를_가진_투표가_없음() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        DoVoteRequest doVoteRequest = new DoVoteRequest(Choice.A);
        voteService.doVote(doVoteRequest.converter(userId, 100L));

        //then
        VoteResult voteResult = voteResultRepository.findByVote(vote);
        assertEquals(vote.getId(), voteResult.getVote().getId());
        assertEquals(user.getId(), voteResult.getVotedUser().getId());
        assertEquals(Choice.A, voteResult.getChoice());
    }

    @Test(expected = AlreadyUserDoVoteException.class)
    public void 투표참여_실패_중복투표() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        DoVoteRequest doVoteRequest = new DoVoteRequest(Choice.A);
        voteService.doVote(doVoteRequest.converter(userId, vote.getId()));
        voteService.doVote(doVoteRequest.converter(userId, vote.getId()));

        //then
        VoteResult voteResult = voteResultRepository.findByVote(vote);
        assertEquals(vote.getId(), voteResult.getVote().getId());
        assertEquals(user.getId(), voteResult.getVotedUser().getId());
        assertEquals(Choice.A, voteResult.getChoice());
    }

    @Test(expected = AlreadyUserDoVoteException.class)
    public void 투표참여_실패_중복투표_수정() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        //when
        DoVoteRequest firstDoVoteRequest = new DoVoteRequest(Choice.A);

        DoVoteRequest secondDoVoteRequest = new DoVoteRequest(Choice.B);

        voteService.doVote(firstDoVoteRequest.converter(userId, vote.getId()));
        voteService.doVote(secondDoVoteRequest.converter(userId, vote.getId()));

        //then
        VoteResult voteResult = voteResultRepository.findByVote(vote);
        assertEquals(vote.getId(), voteResult.getVote().getId());
        assertEquals(user.getId(), voteResult.getVotedUser().getId());
        assertEquals(Choice.A, voteResult.getChoice());
    }

    @Test
    public void 투표결과_조회() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        SignUpRequest secondRequest = new SignUpRequest("testUser2", "test@gmail.com", "password234", Providers.KAKAO, "providerId");
        Long secondUserId = userService.registerUser(secondRequest);

        SignUpRequest thirdRequest = new SignUpRequest("testUser3", "test@nate.com", "password1234", Providers.KAKAO, "providerId");
        Long thirdUserId = userService.registerUser(thirdRequest);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        DoVoteRequest firstDoVoteRequest = new DoVoteRequest(Choice.A);
        DoVoteRequest secondDoVoteRequest = new DoVoteRequest(Choice.B);
        DoVoteRequest thirdDoVoteRequest = new DoVoteRequest(Choice.A);

        voteService.doVote(firstDoVoteRequest.converter(userId, vote.getId()));
        voteService.doVote(secondDoVoteRequest.converter(secondUserId, vote.getId()));
        voteService.doVote(thirdDoVoteRequest.converter(thirdUserId, vote.getId()));

        //when
        long totalVoteCount = statisticsService.getTotalStatistics(vote.getId());
        VoteSelectResultData voteSelectResultData = statisticsService.getSelectedStatistics(vote.getId(), Gender.MALE, Age.twenties, MBTI.ENFJ);

        //then
        assertEquals(3L, totalVoteCount);
        assertEquals(2L, voteSelectResultData.getTotalCountA());
        assertEquals(1L, voteSelectResultData.getTotalCountB());
        assertEquals(66, voteSelectResultData.getPercentA());
        assertEquals(33, voteSelectResultData.getPercentB());

    }

    @Test
    public void 투표결과_조회_실패_아이디를_가진_투표가_없음() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", Providers.KAKAO, "providerId");
        Long userId = userService.registerUser(request);

        SignUpRequest secondRequest = new SignUpRequest("testUser2", "test@gmail.com", "password234", Providers.KAKAO, "providerId");
        Long secondUserId = userService.registerUser(secondRequest);

        SignUpRequest thirdRequest = new SignUpRequest("testUser3", "test@nate.com", "password1234", Providers.KAKAO, "providerId");
        Long thirdUserId = userService.registerUser(thirdRequest);

        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title("투표 제목")
                .imageA("imageA")
                .imageB("imageB")
                .titleA("titleA")
                .titleB("titleB")
                .build();

        voteService.createVote(createVoteRequest,userId);

        Optional<User> userRepositoryByProviderId = userRepository.findById(userId);
        User user = userRepositoryByProviderId.get();

        Optional<Vote> byProviderId = voteRepository.findByPostedUser(user);
        Vote vote = byProviderId.get();

        DoVoteRequest firstDoVoteRequest = new DoVoteRequest(Choice.A);
        DoVoteRequest secondDoVoteRequest = new DoVoteRequest(Choice.B);
        DoVoteRequest thirdDoVoteRequest = new DoVoteRequest(Choice.A);

        voteService.doVote(firstDoVoteRequest.converter(userId, vote.getId()));
        voteService.doVote(secondDoVoteRequest.converter(secondUserId, vote.getId()));
        voteService.doVote(thirdDoVoteRequest.converter(thirdUserId, vote.getId()));

        //when
        long totalVoteCount = statisticsService.getTotalStatistics(vote.getId());
        VoteSelectResultData voteSelectResultData = statisticsService.getSelectedStatistics(vote.getId(), Gender.MALE, Age.twenties, MBTI.ENFJ);

        //then
        assertEquals(3L, totalVoteCount);
        assertEquals(2L, voteSelectResultData.getTotalCountA());
        assertEquals(1L, voteSelectResultData.getTotalCountB());
        assertEquals(66, voteSelectResultData.getPercentA());
        assertEquals(33, voteSelectResultData.getPercentB());

    }

}

