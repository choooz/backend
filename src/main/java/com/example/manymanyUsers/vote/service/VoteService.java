package com.example.manymanyUsers.vote.service;

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
import com.example.manymanyUsers.vote.enums.VoteType;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;
    private final BookmarkRepository bookmarkRepository;


    public Long createVote(@Valid CreateVoteRequest createVoteRequest, Long userId) throws UserNotFoundException {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Vote vote = Vote.builder()
                .category(Category.ETC)
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

        if (voteResultRepository.existsByVoteAndVotedUser(vote, user)) {
            throw new AlreadyUserDoVoteException();
        }

        VoteResult voteResult = new VoteResult();

        voteResult.doVote(vote, user, doVote.getChoice());

        voteResultRepository.save(voteResult);

    }

    public Slice<VoteListData> getVoteList(SortBy sortBy, Integer page, Integer size, Category category) {

        Slice<VoteListData> voteListData = null;

        if (sortBy.equals(SortBy.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            voteListData = getVoteSortByTime(category, pageRequest);
        } else if (sortBy.equals(SortBy.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            voteListData = getVoteByPopularity(category, pageRequest);
        } else {
            throw new RuntimeException("잘못된 요청입니다.");
        }

        return voteListData;
    }

    private Slice<VoteListData> getVoteSortByTime(Category category, PageRequest pageRequest) {
        Slice<VoteListData> voteListData;

        Slice<FindVoteListData> sliceBy = voteRepository.findSliceBy(category, pageRequest);
        voteListData = sliceBy.map(findVoteListData -> new VoteListData(findVoteListData.getVote(), findVoteListData.getCnt()));


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

    public Slice<VoteListData> getSearchVoteList(String keyword, SortBy sortBy, int page, int size, Category category) {

        Slice<Vote> searchedVoteSlice = null;

        if(sortBy.equals(SortBy.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            searchedVoteSlice = voteRepository.findSliceByCategoryOrCategoryNullAndTitleContains(category, keyword, pageRequest);
        } else if(sortBy.equals(SortBy.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            searchedVoteSlice = voteRepository.findSliceByCategoryOrCategoryNullAndTitleContainsPopularity(category, keyword, pageRequest);
        }

//        if (category == null) {
//            voteSlice = voteRepository.findSliceByTitleContains(keyword, pageRequest);
//        }else{
//            voteSlice = voteRepository.findSliceByCategoryAndTitleContains(category, keyword, pageRequest);
//        }

        Slice<VoteListData> voteListData = searchedVoteSlice.map(vote -> {
            vote.getPostedUser(); //프록시 처리된 user 엔티티 가져오기 위함
            Long countVoted = voteResultRepository.countByVote(vote);
            return new VoteListData(vote, countVoted);
        });
        return voteListData;
    }


    public Vote getVote(Long voteId) {
        return voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
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

    public Slice<Vote> getVotesByUser(Long userId, VoteType type, int page, int size) {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Slice<Vote> voteList = null;
        PageRequest pageRequest = PageRequest.of(page, size);
        //작성한 vote
        if (type == VoteType.created) {
            voteList = voteRepository.findAllByPostedUser(findUser, pageRequest);
        }
        //참여한 vote
        else if (type == VoteType.participated) {
            voteList = voteRepository.findParticipatedVoteByUser(findUser, pageRequest);
        }
        //북마크한 vote
        else if (type == VoteType.bookmarked) {
            voteList = voteRepository.findBookmarkedVoteByUser(findUser, pageRequest);
        }
        return voteList;
    }

    public List<String> getRecommendVoteList(String keyword) {
        return voteRepository.findByCategoryAndTitleContains(keyword).stream()
                .limit(5)
                .map(Vote::getTitle)
                .collect(Collectors.toList());
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


    public GetIsUserVoted isUserVoted(Long voteId, Long userId) {
        GetIsUserVoted getIsUserVoted = new GetIsUserVoted(false, null);
        voteResultRepository.getVoteResultByVoteIdAndUserId(voteId, userId).ifPresent(voteResult -> {
            getIsUserVoted.setVoted(true);
            getIsUserVoted.setUserChoice(voteResult.getChoice());
        });
        return getIsUserVoted;
    }

    public boolean checkBookmarked(Long userId, Long voteId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Optional<Bookmark> byVoteAndUser = bookmarkRepository.findByVoteAndUser(vote, user);

        return bookmarkRepository.findByVoteAndUser(vote, user).isPresent();
    }
}
