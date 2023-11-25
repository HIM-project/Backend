package HIM.project.dto.kakao;

import HIM.project.entity.OpeningTime;
import HIM.project.entity.Restaurant;
import HIM.project.entity.type.Day;
import lombok.*;

import java.sql.Time;
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
        private Time open;
        private Time close;
    }

}

