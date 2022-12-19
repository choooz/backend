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
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.service.VoteService;
import org.junit.Assert;
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
        String userProviderId = userService.registerUser(request);

        //when
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(
                userProviderId,
                "투표 제목",
                "imageA",
                "imageB",
                "detailText",
                Gender.NULL,
                Age.NULL,
                Category.NULL);

        voteService.createVote(createVoteRequest);

        Optional<Vote> byProviderId = voteRepository.findByProviderId(createVoteRequest.getProviderId());
        Vote vote = byProviderId.get();

        Optional<User> userRepositoryByProviderId = userRepository.findByProviderId(createVoteRequest.getProviderId());
        User user = userRepositoryByProviderId.get();

        //then
        assertEquals(vote.getPostedUser(), user);
        assertEquals(vote.getTitle(), createVoteRequest.getTitle());
        assertEquals(vote.getCategory(), createVoteRequest.getCategory());
        assertEquals(vote.getDetail(), createVoteRequest.getDetail());
        assertEquals(vote.getImageA(), createVoteRequest.getImageA());
        assertEquals(vote.getImageB(), createVoteRequest.getImageB());
        assertEquals(vote.getFilteredAge(), createVoteRequest.getGender());
        assertEquals(vote.getFilteredAge(), createVoteRequest.getAge());
        assertEquals(vote.getCategory(), createVoteRequest.getCategory());
    }
}
