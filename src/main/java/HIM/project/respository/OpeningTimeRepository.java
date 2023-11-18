package HIM.project.respository;

import HIM.project.entity.OpeningTime;
import HIM.project.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OpeningTimeRepository extends JpaRepository<OpeningTime,String> {
    List<OpeningTime> findAllByRestaurant(Restaurant restaurant);
}
