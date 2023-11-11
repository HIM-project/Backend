package HIM.project.entity.type;


import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum Category {
    KOREAN("한식"),
    WESTERN("양식"),
    ASIAN("아시아음식"),
    JAPAN("일식"),
    CHINESE("중식"),
    SNACK("분식"),
    CAFE("카페"),
    BUFFET("뷔페"),
    OTHERS("기타");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        Arrays.stream(Category.values()).forEach(type -> values.add(type.getValue()));
        return values;
    }
}
