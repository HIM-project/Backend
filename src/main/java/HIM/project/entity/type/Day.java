package HIM.project.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Day {
        MONDAY("월"),
        TUESDAY("화"),
        WEDNESDAY ("수"),
        THURSEDAY("목"),
        FRIDAY("금"),
        SATURDAY("토"),
        SUNDAY("일");

        private final String value;

}
