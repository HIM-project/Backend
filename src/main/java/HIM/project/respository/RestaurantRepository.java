package HIM.project.respository;


import HIM.project.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {
    Restaurant findAllByCrNumber(String crNumber);
}
