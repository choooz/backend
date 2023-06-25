package kr.co.chooz.kakao.response;

import lombok.Data;

@Data
public class KakaoUserInfo {
    private Long id;
    private KakaoUserProperties properties;

    public String getNickName() {
        return properties.getNickname();
    }

    public String getProfileImage() {
        return properties.getProfile_image();
    }
}

@Data
class KakaoUserProperties {
    private String nickname;
    private String profile_image;
}
