package kr.co.chooz.user.request;

import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import kr.co.chooz.user.dto.AddUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddInfoRequest {

    @NotBlank(message = "mbti는 blank일 수 없습니다.")
    private MbtiType mbti;

    @NotBlank(message = "age는 blank일 수 없습니다.")
    private Integer age;

    @NotBlank(message = "gender는 blank일 수 없습니다.")
    private GenderType gender;

    public AddUserInfo toAddUserInfo() {
        return new AddUserInfo(mbti, age, gender);
    }

}
