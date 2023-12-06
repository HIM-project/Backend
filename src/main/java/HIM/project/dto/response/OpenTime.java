package HIM.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class OpenTime{

    private String day;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalTime breakOpen;

    private LocalTime breakClose;

    @QueryProjection
    public OpenTime(String day, LocalTime openTime, LocalTime closeTime, LocalTime breakOpen, LocalTime breakClose) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakOpen = breakOpen;
        this.breakClose = breakClose;
    }
}