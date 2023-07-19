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
public class NaverLoginRequest {

    @Schema(description = "Oauth 서버에서 받아온 인가코드", example = "인가코드")
    @NotBlank
    private String code;

    /**
     * state 값은 CSRF를 방지하기 위한 인증값으로 임의의 값을 넣어줄 수 있음.네이버 로그인시 요청한 state 값 과 동일한 값으로 받아와야함
     * CSRF는 Cross Site Request Forgery(사이트 간 요청 위조)의 줄임말로 웹 취약점 중 하나이다.
     */
    @Schema(description = "네이버 로그인 CSRF를 방지하기 위한 인증값", example = "string")
    private String state;


    public ThirdPartySignupInfo toDomain() {
        Map<String, String> propertiesValues = new HashMap<>();
        propertiesValues.put("code", code);
        propertiesValues.put("state", state);
        return new ThirdPartySignupInfo(ProviderType.NAVER, propertiesValues);
    }
}
