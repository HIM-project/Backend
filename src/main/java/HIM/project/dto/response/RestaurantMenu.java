package HIM.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantMenu {
    private String menuThumbnail;
    private String menuName;
    private Integer price;

    @QueryProjection
    public RestaurantMenu(String menuThumbnail, String menuName, Integer price) {
        this.menuThumbnail = menuThumbnail;
        this.menuName = menuName;
        this.price = price;
    }
}
