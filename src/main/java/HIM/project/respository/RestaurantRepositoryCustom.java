package HIM.project.respository;

import HIM.project.dto.response.MyRestaurant;
import HIM.project.dto.response.RestaurantInfo;
import HIM.project.entity.Restaurant;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<Restaurant> findAllListByRestaurantId(Long restaurantId);

    List<MyRestaurant> findAllByUserUserId(Long userId);

    RestaurantInfo findByRestaurantId(Long restaurantId);

    Restaurant findAllByRestaurantId(Long restaurantId);
}
