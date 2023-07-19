package kr.co.chooz.vote.dto;

import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VotedUserInfo {
    private String userImage;

    private GenderType userGender;

    private AgeType userAge;

    private MbtiType userMbti;

    private String nickName;

    @Builder
    public VotedUserInfo(String userImage, GenderType userGender, AgeType userAge, MbtiType userMbti, String nickName) {
        this.userImage = userImage;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userMbti = userMbti;
        this.nickName = nickName;
    }
}
