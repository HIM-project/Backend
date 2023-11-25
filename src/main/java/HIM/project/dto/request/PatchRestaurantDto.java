package HIM.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchRestaurantDto {
    private Long restaurantId;
    private String restaurantExplanation;
}
