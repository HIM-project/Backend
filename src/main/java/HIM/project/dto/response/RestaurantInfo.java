package HIM.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class RestaurantInfo {
    private String restaurantName;

    private Long reviewCount;

    private Double starPoint;

    private String restaurantThumbnail;

    private String reviewThumbnail;

    private LocalTime restaurantTime;

    private String category;

    private Boolean servicing;

    @QueryProjection
    public RestaurantInfo(String restaurantName, Long reviewCount, Double starPoint, String restaurantThumbnail, String reviewThumbnail, LocalTime restaurantTime, String category, Boolean servicing) {
        this.restaurantName = restaurantName;
        this.reviewCount = reviewCount;
        this.starPoint = starPoint;
        this.restaurantThumbnail = restaurantThumbnail;
        this.reviewThumbnail = reviewThumbnail;
        this.restaurantTime = restaurantTime;
        this.category = category;
        this.servicing = servicing;
    }

    public static class OpenTime {

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
}
