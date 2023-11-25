package HIM.project.dto.response;


import HIM.project.entity.Restaurant;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.querydsl.binding.QuerydslPredicate;


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

    public static MyRestaurant from(Restaurant restaurant){
        return MyRestaurant.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantThumbnail(restaurant.getRestaurantThumbnail())
                .build();

    }
}
