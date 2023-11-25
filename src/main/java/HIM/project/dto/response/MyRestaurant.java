package HIM.project.dto.response;


import HIM.project.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MyRestaurant {
    private Long restaurantId;
    private String restaurantThumbnail;
    private String restaurantName;

    public static MyRestaurant from(Restaurant restaurant){
        return MyRestaurant.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantThumbnail(restaurant.getRestaurantThumbnail())
                .build();

    }
}
