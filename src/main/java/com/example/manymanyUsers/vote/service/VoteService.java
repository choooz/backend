package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.CreateVoteResponse;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity createVote(@Valid CreateVoteRequest createVoteRequest) {
        Optional<User> find = userRepository.findByEmail(createVoteRequest.getPostedUserEmail());
        if(find.isEmpty()){
            CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                    .message("이메일로 가입된 유저가 없습니다. 이메일을 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }

        User user = find.get();

        Vote vote = new Vote();

        vote.setPostedUser(user);
        vote.setTitle(createVoteRequest.getTitle());
        vote.setImageA(createVoteRequest.getImageA());
        vote.setImageB(createVoteRequest.getImageB());
        vote.setDetail(createVoteRequest.getDetail());
        vote.setFilteredGender(createVoteRequest.getGender());
        vote.setFilteredAge(createVoteRequest.getAge());
        vote.setCategory(createVoteRequest.getCategory());

        voteRepository.save(vote);

        CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                .message("투표 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }
}
