package kr.co.chooz.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class KakaoLoginRequest {

    @Schema(description = "Oauth 서버에서 받아온 인가코드", example = "인가코드")
    @NotBlank
    private String code;

    /**
     * redirectUrl 은 인가코드를 받아올 redirectUrl을 의미하며 여기서  redirectUrl은 카카오 로그인시 요청한 redirectUrl과 동일한 값으로 받아와야함
     * 리다이렉트 유알엘을 받는 이유는 로컬, 배포 , 테스트 환경에서 유동적으로 실행할수있게 하기 위함임
     */
    @Schema(description = "카카오 로그인후 리다이렉트 받을 주소 경로", example = "http://localhost:3000/login/kakaoLoginProcess")
    private String redirectUrl;


    public ThirdPartySignupInfo toDomain() {
        Map<String, String> propertiesValues = new HashMap<>();
        propertiesValues.put("code", code);
        propertiesValues.put("redirectUrl", redirectUrl);
        return new ThirdPartySignupInfo(ProviderType.KAKAO, propertiesValues);
    }

}
