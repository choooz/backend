package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.comment.domain.CommentEmotion;
import com.example.manymanyUsers.comment.enums.Emotion;
import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.AlreadyUserDoVoteException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Bookmark;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.repository.BookmarkRepository;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;

    private final BookmarkRepository bookmarkRepository;



    public Long createVote(@Valid CreateVoteRequest createVoteRequest, Long userId) throws UserNotFoundException{
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Vote vote = Vote.builder()
                .postedUser(findUser)
                .title(createVoteRequest.getTitle())
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

        //김민엽
//        voteResult.mappingVote(vote);

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



//    private Slice<VoteListData> getVoteByPopularity(Category category, PageRequest pageRequest) {
//
//        Slice<VoteResult> voteSlice = voteResultRepository.findWithVoteFROMResult(category, pageRequest);
//
//        Slice<VoteListData> voteListData = voteSlice.map(voteResult -> {
////            Long countVoted = voteResultRepository.countByVote(voteResult.getVote());
//            return new VoteListData(voteResult.getVote());
//        });
//        return voteListData;
//    }

    public Slice<VoteListData> getSearchVoteList(String keyword, SortBy sortBy, int page, int size, Category category) {

        Slice<Vote> voteSlice;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));

        if (category == null) {
            voteSlice = voteRepository.findSliceByTitleContains(keyword, pageRequest);
        }else{
            voteSlice = voteRepository.findByCategoryAndTitleContains(category, keyword, pageRequest);
        }

        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            vote.getPostedUser(); //프록시 처리된 user 엔티티 가져오기 위함
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

    public List<Vote> getVotesByUser(Long userId, String type) {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Vote> voteList = new ArrayList<>();
        //작성한 vote
        if (type.equals("created")){
            voteList=voteRepository.findAllByPostedUser(findUser);
        }
        //참여한 vote
        else if(type.equals("participated")){
            voteList=voteRepository.findParticipatedVoteByUser(findUser);
        }
        //북마크한 vote
//        else if(type.equals("bookmarked")){
//            voteList=voteRepository.findAllByBookmarked(findUser);
//        }
        return voteList;
    }

    public void bookmarkVote(Long userId, Long voteId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Optional<Bookmark> byVoteAndUser = bookmarkRepository.findByVoteAndUser(vote, user);

        byVoteAndUser.ifPresentOrElse(
                bookmark -> {
                    //북마크를 눌렀는데 또 눌렀을 경우 북마크 취소
                    bookmarkRepository.delete(bookmark);
                    vote.removeBookmark(bookmark);
                },
                // 북마크가 없을 경우 북마크 추가
                () -> {
                    Bookmark bookmark = new Bookmark();

                    bookmark.mappingVote(vote);
                    bookmark.mappingUser(user);

                    bookmarkRepository.save(bookmark);

                }
        );

    }
}
