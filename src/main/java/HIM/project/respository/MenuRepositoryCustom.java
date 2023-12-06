package HIM.project.respository;

import HIM.project.dto.response.RestaurantMenu;
import HIM.project.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepositoryCustom {
    Optional<Menu> findAllByMenuIdAndIsDeletedIsFalse(Long menuId);

    List<RestaurantMenu> findAllMenuByRestaurantIdAndIsDeletedIsFalseAndOrderBySortMenu(Long restaurantId);
}
