package HIM.project.entity;


import HIM.project.common.ErrorCode;
import HIM.project.entity.type.Category;
import HIM.project.dto.RegisterDto;
import HIM.project.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Builder
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "category")
    private String category;

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

    public static Restaurant of(RegisterDto registerDto,User user) {
        String categoryEnum = registerDto.getCategory().getValue();

        return builder()
                .user(user)
                .category(categoryEnum)
                .restaurantName(registerDto.getRestaurantName())
                .restaurantExplanation(registerDto.getRestaurantExplanation())
                .restaurantThumbnail(registerDto.getRestaurantThumbnail())
                .restaurantLocation(registerDto.getRestaurantLocation())
                .restaurantNumber(registerDto.getRestaurantNumber())
                .crNumber(registerDto.getCrNumber())
                .openingHours(registerDto.getOpeningHours())
                .build();
    }
}
