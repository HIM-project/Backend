package HIM.project.respository;


import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {
    Optional<Restaurant> findAllByCrNumber(String crNumber);

    Optional<Restaurant> findAllByUser(User user);
}
