package HIM.project.respository;


import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {
    Boolean existsAllByCrNumber(String crNumber);

    Optional<List<Restaurant>> findAllByUserUserId(Long userId);

    Optional<Restaurant> findAllByRestaurantId(Long restaurantId);

    Optional<Restaurant> findAllByUser(User user);
}
