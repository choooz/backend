package com.example.manymanyUsers.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.service.VoteService;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                userId,
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

        voteService.createVote(createVoteRequest);

        Optional<User> userRepositoryByProviderId = userRepository.findById(createVoteRequest.getUserId());
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
        SignUpRequest request = new SignUpRequest("testUser", "test@naver.com", "password", "", "providerId");
        Long userId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                userId,
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
        voteService.createVote(createVoteRequest);

    }
}
