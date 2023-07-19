package kr.co.chooz.user.dto;

import kr.co.chooz.user.domain.entitiy.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserCategory {

    private List<String> categories;
}
