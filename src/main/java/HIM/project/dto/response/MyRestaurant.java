package HIM.project.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MyRestaurant {
    private Long restaurantId;
    private String restaurantThumbnail;
    private String restaurantName;

    @QueryProjection
    public MyRestaurant(Long restaurantId, String restaurantThumbnail, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantThumbnail = restaurantThumbnail;
        this.restaurantName = restaurantName;
    }
}
