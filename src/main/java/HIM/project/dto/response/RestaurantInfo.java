package HIM.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
@Getter
@Builder
public class RestaurantInfo {
    private String restaurantName;

    private Double starPoint;

    private String restaurantThumbnail;

    private String reviewThumbnail;

    private Time restaurantTime;

    private String category;

    @QueryProjection
    public RestaurantInfo(String restaurantName, Double starPoint, String restaurantThumbnail, String reviewThumbnail, Time restaurantTime, String category) {
        this.restaurantName = restaurantName;
        this.starPoint = starPoint;
        this.restaurantThumbnail = restaurantThumbnail;
        this.reviewThumbnail = reviewThumbnail;
        this.restaurantTime = restaurantTime;
        this.category = category;
    }
}
