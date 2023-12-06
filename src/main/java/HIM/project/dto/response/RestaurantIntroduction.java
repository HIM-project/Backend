package HIM.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantIntroduction {
    private String restaurantName;

    private Long reviewCount;

    private Double starPoint;

    private String restaurantThumbnail;

    private String category;

    private Boolean servicing;

    private String restaurantExplanation;


    @QueryProjection
    public RestaurantIntroduction(String restaurantName, Long reviewCount, Double starPoint, String restaurantThumbnail, String category, Boolean servicing, String restaurantExplanation) {
        this.restaurantName = restaurantName;
        this.reviewCount = reviewCount;
        this.starPoint = starPoint;
        this.restaurantThumbnail = restaurantThumbnail;
        this.category = category;
        this.servicing = servicing;
        this.restaurantExplanation = restaurantExplanation;
    }


}
