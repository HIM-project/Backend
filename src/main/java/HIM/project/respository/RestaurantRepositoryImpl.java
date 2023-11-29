package HIM.project.respository;

import HIM.project.common.ErrorCode;
import HIM.project.dto.response.MyRestaurant;
import HIM.project.dto.response.QMyRestaurant;
import HIM.project.entity.QRestaurant;
import HIM.project.entity.QUser;
import HIM.project.entity.Restaurant;
import HIM.project.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Restaurant> findAllByRestaurantId(Long restaurantId) {
        QRestaurant restaurant = QRestaurant.restaurant;

        return jpaQueryFactory
                .selectFrom(restaurant)
                .where(restaurant.restaurantId.eq(restaurantId))
                .fetch();
    }

    @Override
    public List<MyRestaurant> findAllByUserUserId(Long userId) {
        QRestaurant restaurant = QRestaurant.restaurant;
        QUser user = QUser.user;

        return Optional.ofNullable(jpaQueryFactory.select(new QMyRestaurant(
                        restaurant.restaurantId,
                        restaurant.restaurantThumbnail,
                        restaurant.restaurantName
                ))
                .from(restaurant)
                .join(restaurant.user, user)
                .where(user.userId.eq(userId))
                .fetch()).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }
}
