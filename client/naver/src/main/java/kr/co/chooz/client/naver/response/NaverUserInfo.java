package kr.co.chooz.client.naver.response;

import lombok.Data;

@Data
public class NaverUserInfo {
    private NaverUserProperties response;

    public String getNickName() {
        return response.getNickname();
    }

    public String getProfileImage() {
        return response.getProfile_image();
    }

    public String getId() {
        return response.getId();
    }

}

@Data
class NaverUserProperties {
    private String id;
    private String nickname;
    private String profile_image;

}
