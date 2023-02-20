package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.AlreadyUserDoVoteException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.DoVote;
import com.example.manymanyUsers.vote.dto.UpdateVoteRequest;
import com.example.manymanyUsers.vote.dto.VoteListData;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;



    public Long createVote(@Valid CreateVoteRequest createVoteRequest, Long userId) throws UserNotFoundException{
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Vote vote = Vote.builder()
                .postedUser(findUser)
                .totalTitle(createVoteRequest.getTitle())
                .imageA(createVoteRequest.getImageA())
                .imageB(createVoteRequest.getImageB())
                .titleA(createVoteRequest.getTitleA())
                .titleB(createVoteRequest.getTitleB())
                .build();

        voteRepository.save(vote);

        return vote.getId();

    }


    public void doVote(DoVote doVote) {

        Vote vote = voteRepository.findById(doVote.getVoteId()).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(doVote.getUserId()).orElseThrow(UserNotFoundException::new);

        if(voteResultRepository.existsByVoteAndVotedUser(vote, user)) {
            throw new AlreadyUserDoVoteException();
        }

        VoteResult voteResult = new VoteResult();

        voteResult.doVote(vote, user, doVote.getChoice());

        voteResultRepository.save(voteResult);

    }

    public Slice<VoteListData> getVoteList(SortBy sortBy, Integer page, Integer size, Category category){

        Slice<VoteListData> voteListData = null;

        if(sortBy.equals(SortBy.ByTime)){
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            voteListData = getVoteSortByTime(category, pageRequest);
        } else if (sortBy.equals(SortBy.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            voteListData = getVoteByPopularity(category, pageRequest);
        }else {
            throw new RuntimeException("잘못된 요청입니다.");
        }

        return voteListData;
    }

    private Slice<VoteListData> getVoteSortByTime(Category category, PageRequest pageRequest) {

        Slice<Vote> voteSlice;

        if (category == null) {
            voteSlice = voteRepository.findSliceBy(pageRequest);
        }else{
            voteSlice = voteRepository.findByCategory(category, pageRequest);
        }

        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            vote.getPostedUser(); //프록시 처리된 user 엔티티 가져오기 위함
            Long countVoted = voteResultRepository.countByVote(vote);
            return new VoteListData(vote, countVoted);
        });

        return voteListData;
    }

    private Slice<VoteListData> getVoteByPopularity(Category category, PageRequest pageRequest) {

        Slice<Vote> voteSlice = voteRepository.findWithVoteResult(category, pageRequest);

        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            Long countVoted = voteResultRepository.countByVote(vote);
            return new VoteListData(vote, countVoted);
        });
        return voteListData;
    }


    public Vote getVote(Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        return vote;
    }

    public void updateVote(@Valid UpdateVoteRequest updateVoteRequest, Long userId, Long voteId) throws UserNotFoundException, VoteNotFoundException {

        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        vote.update(updateVoteRequest);
    }

    public void deleteVote(Long voteId, Long userId) {

        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        voteRepository.deleteById(voteId);

    }

}
