package HIM.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantInfoResponse {
    private RestaurantIntroduction restaurantIntroduction;
    private List<OpenTime> openingTimes;

}
