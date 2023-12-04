package HIM.project.dto.kakao;

import HIM.project.entity.type.Day;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OpeningDtoList {
    private List<OpeningDto> openingDtoList;

    @ToString
    @Getter
    public static class OpeningDto {
        private Long restaurantId;
        private Day day;
        private LocalTime open;
        private LocalTime close;
    }

}

