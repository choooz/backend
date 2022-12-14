package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.GetVoteListRequest;
import com.example.manymanyUsers.vote.dto.VoteListData;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public Vote createVote(@Valid CreateVoteRequest createVoteRequest, Long userid) throws NotFoundException{
        Optional<User> find = userRepository.findById(userid);
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

        return voteRepository.save(vote);

    }


    public void doVote() {

    }

    public Slice<VoteListData> getVoteList(SortBy soryBy, Integer page, Integer size){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, soryBy.getValue()));
        Slice<Vote> voteSlice = voteRepository.findSliceBy(pageRequest);
        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            vote.getPostedUser(); //프록시 처리된 user 엔티티 가져오기 위함
            return new VoteListData(vote);
        });
        return voteListData;
    }


}
