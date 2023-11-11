package HIM.project.entity;


import HIM.project.entity.type.Category;
import HIM.project.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "category")
    private Category category;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_thumbnail")
    private String restaurantThumbnail;

    @Column(name = "restaurant_location")
    private String restaurantLocation;

    @Column(name = "restaurant_explanation")
    private String restaurantExplanation;

    @Column(name = "restaurant_number")
    private String restaurantNumber;

    @Column(name = "restaurant_open_count")
    private Long restaurantOpenCount;

    @Column(name = "opening_hours")
    private LocalDateTime openingHours;

    @Column(name = "cr_number")
    private String crNumber;

    public static Restaurant of(RegisterDto registerDto) {
        Category categoryEnum = Category.valueOf(registerDto.getCategory());

        return builder()
                .category(categoryEnum)
                .restaurantName(registerDto.getRestaurantName())
                .restaurantThumbnail(registerDto.getRestaurantThumbnail())
                .restaurantLocation(registerDto.getRestaurantLocation())
                .restaurantNumber(registerDto.getRestaurantNumber())
                .crNumber(registerDto.getCrNumber())
                .openingHours(registerDto.getOpeningHours())
                .build();
    }
}
