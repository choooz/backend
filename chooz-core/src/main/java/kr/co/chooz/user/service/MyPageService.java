package kr.co.chooz.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class MyPageService {

//    private final UserRepository userRepository;
//    private final VoteRepository voteRepository;
//    private final VoteResultRepository voteResultRepository;
//    private final BookmarkRepository bookmarkRepository;

    public Map<String, Long> getMyPageCount(Long userId) {
//        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//        Map<String, Long> map = new HashMap<>();
//
//        Long createdVoteCount = 0L;
//        Long participatedVoteCount = 0L;
//        Long bookmarkedVoteCount = 0L;
//
//
//        createdVoteCount = voteRepository.countVoteByPostedUser(findUser);
//        participatedVoteCount = voteResultRepository.countByVotedUser(findUser);
//        bookmarkedVoteCount = bookmarkRepository.countByUser(findUser);
//
//        map.put("CREATED_VOTE", createdVoteCount);
//        map.put("PARTICIPATED_VOTE", participatedVoteCount);
//        map.put("BOOKMARKED_VOTE", bookmarkedVoteCount);
//
//        return map;
    }
}
