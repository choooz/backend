package kr.co.chooz.user.domain.entitiy;

import kr.co.chooz.user.domain.vote.CreateVoteInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Vote {

    private Long id;
    private String title;
    private String detail;

    public Vote(CreateVoteInfo info) {
        this.title = info.getTitle();
    }

//    public void removeBookmark(Bookmark bookmark) {
//        this.bookmarkList.remove(bookmark);
//    }

//    @Builder
//    public Vote(User postedUser, String title, String imageA, String imageB, String titleA, String titleB, String detail, Category category, Gender filteredGender, Age filteredAge, MBTI filteredMbti) {
//        this.postedUser = postedUser;
//        this.title = title;
//        this.imageA = imageA;
//        this.imageB = imageB;
//        this.titleA = titleA;
//        this.titleB = titleB;
//        this.detail = detail;
//        this.category = category;
//        this.filteredGender = filteredGender;
//        this.filteredAge = filteredAge;
//        this.filteredMbti = filteredMbti;
//    }
//
//    public void  update(UpdateVoteRequest updateVoteRequest) {
//        this.title = updateVoteRequest.getTitle();
//        this.titleA = updateVoteRequest.getTitleA();
//        this.titleB = updateVoteRequest.getTitleB();
//        this.detail = updateVoteRequest.getDetail();
//        this.category = updateVoteRequest.getCategory();
//    }
//
//    public void addVoteResult(VoteResult voteResult) {
//        this.voteResultList.add(voteResult);
//    }
//
//    public void mappingBookmark(Bookmark bookmark) {
//        this.bookmarkList.add(bookmark);
//    }


}