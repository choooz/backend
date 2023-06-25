package kr.co.chooz.client.naver.response;

import lombok.Data;

@Data
public class NaverUserInfo {

    private Long id;
    private NaverUserProperties properties;

    public String getNickName() {
        return properties.getNickname();
    }

    public String getProfileImage() {
        return properties.getProfile_image();
    }

}

@Data
class NaverUserProperties {

    private String nickname;
    private String profile_image;

}
