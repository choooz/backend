package kr.co.chooz.user.request;

import kr.co.chooz.user.dto.AddUserCategory;
import kr.co.chooz.user.dto.AddUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryRequest {

    @NotNull(message = "categories는 null 일 수 없습니다.")
    private List<String> categories;

    public AddUserCategory toAddUserCategory() {
        return new AddUserCategory(categories);
    }

}
