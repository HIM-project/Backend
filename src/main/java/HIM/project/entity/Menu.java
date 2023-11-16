package HIM.project.entity;


import HIM.project.dto.MenuDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Builder
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id",nullable = false)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "food_thumbnail")
    private String foodThumbnail;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    public static Menu of(MenuDto menuDto, Restaurant restaurant, String foodThumbnail)  {

        return Menu.builder()
                .restaurant(restaurant)
                .foodName(menuDto.getFoodName())
                .foodThumbnail(foodThumbnail)
                .isDeleted(false)
                .price(menuDto.getFoodPrice())
                .build();
    }

}
