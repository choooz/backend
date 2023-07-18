package kr.co.chooz.user.domain.entitiy;

import lombok.Getter;

import java.util.List;

@Getter
public class Categories {

    private final List<String> values;

    public Categories(List<String> values) {
        this.values = values;
    }

    public static Categories of(List<String> values) {
        return new Categories(values);
    }
}
