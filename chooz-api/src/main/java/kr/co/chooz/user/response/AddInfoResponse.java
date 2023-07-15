package kr.co.chooz.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddInfoResponse {

    private final Long userId;

    @Builder
    private AddInfoResponse(Long userId) {
        this.userId = userId;
    }

    public static AddInfoResponse of(Long userId) {
        return AddInfoResponse.builder()
                .userId(userId)
                .build();
    }
}
