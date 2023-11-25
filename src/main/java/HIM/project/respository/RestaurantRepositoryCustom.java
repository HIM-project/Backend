package HIM.project.respository;

import HIM.project.dto.response.MyRestaurant;
import HIM.project.entity.Restaurant;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<Restaurant> findAllByRestaurantId(Long restaurantId);

    List<MyRestaurant> findAllByUserUserId(Long userId);
}
