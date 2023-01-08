package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.UpdateVoteRequest;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public void createVote(@Valid CreateVoteRequest createVoteRequest) throws NotFoundException{
        Optional<User> find = userRepository.findById(createVoteRequest.getUserId());
        if(find.isEmpty()){
            throw new NotFoundException("해당 아이디를 가진 유저가 없습니다. 아이디 값을 다시 한번 확인하세요.");
        }

        User user = find.get();

        Vote vote = new Vote();

        vote.setPostedUser(user);
        vote.setTotalTitle(createVoteRequest.getTitle());
        vote.setImageA(createVoteRequest.getImageA());
        vote.setImageB(createVoteRequest.getImageB());
        vote.setTitleA(createVoteRequest.getTitleA());
        vote.setTitleB(createVoteRequest.getTitleB());
        vote.setDetail(createVoteRequest.getDetail());
        vote.setFilteredGender(createVoteRequest.getFilteredGender());
        vote.setFilteredAge(createVoteRequest.getFilteredAge());
        vote.setCategory(createVoteRequest.getCategory());
        vote.setFilteredMbti(createVoteRequest.getFilteredMbti());

        voteRepository.save(vote);

    }


    public void doVote() {

    }

    public void updateVote(@Valid UpdateVoteRequest updateVoteRequest) throws NotFoundException{
        Optional<Vote> findVote = voteRepository.findById(updateVoteRequest.getVoteId());
        if(findVote.isEmpty()) {
            throw new NotFoundException("해당 아이디를 가진 투표가 없습니다. 아이디 값을 다시 한번 확인하세요.");
        }

        Vote vote = findVote.get();

        vote.setTotalTitle(updateVoteRequest.getTitle());
        vote.setImageA(updateVoteRequest.getImageA());
        vote.setImageB(updateVoteRequest.getImageB());
        vote.setTitleA(updateVoteRequest.getTitleA());
        vote.setTitleB(updateVoteRequest.getTitleB());
        vote.setDetail(updateVoteRequest.getDetail());
        vote.setFilteredGender(updateVoteRequest.getFilteredGender());
        vote.setFilteredAge(updateVoteRequest.getFilteredAge());
        vote.setCategory(updateVoteRequest.getCategory());
        vote.setFilteredMbti(updateVoteRequest.getFilteredMbti());

    }

    public void deleteVote(Long voteId) {

        voteRepository.deleteById(voteId);

    }
}
