package HIM.project.respository;

import HIM.project.common.ErrorCode;
import HIM.project.dto.response.*;
import HIM.project.entity.*;
import HIM.project.entity.type.Day;
import HIM.project.exception.CustomException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Restaurant> findAllListByRestaurantId(Long restaurantId) {
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

    public RestaurantInfo restaurantInfoFindByRestaurantId(Long restaurantId) {
        QRestaurant restaurant = QRestaurant.restaurant;
        QReview review = QReview.review;
        QOpeningTime openingTime = QOpeningTime.openingTime;

        String koreanDay = getKoreanDay();


        return Optional.ofNullable(jpaQueryFactory.select(new QRestaurantInfo(
                        restaurant.restaurantName,
                        reviewCount(review,restaurantId),
                        avgReviewStar(review),
                        restaurant.restaurantThumbnail.coalesce(""),
                        review.reviewThumbnail.max().coalesce(""),
                        openingTime.closeTime.max(),
                        restaurant.category.coalesce(""),
                        isServicing(openingTime,getKoreanDay(),LocalTime.now(),restaurantId)
                )).from(restaurant)
                .leftJoin(review).on(restaurant.restaurantId.eq(review.restaurant.restaurantId))
                .leftJoin(openingTime)
                .on(restaurant.restaurantId.eq(openingTime.restaurant.restaurantId)
                        .and(openingTime.day.eq(koreanDay)))
                .where(restaurant.restaurantId.eq(restaurantId))
                .orderBy(review.reviewId.desc())
                .fetchOne()).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_INFO_NOT_FOUND));
    }

    private  NumberExpression<Double> avgReviewStar(QReview review) {
        return Expressions.numberTemplate(Double.class, "ROUND(COALESCE({0}, 0),1)", review.starPoint.avg()).coalesce(0.0);
    }

    private JPAQuery<Long> reviewCount(QReview review, Long restaurantId) {
        return jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(review.restaurant.restaurantId.eq(restaurantId));
    }

    private BooleanExpression isServicing(QOpeningTime openingTime, String koreanDay, LocalTime nowTime,Long restaurantId) {
        return jpaQueryFactory.from(openingTime)
                .where(openingTime.restaurant.restaurantId.eq(restaurantId)
                        .and(openingTime.day.eq(koreanDay))
                        .and(
                                openingTime.openTime.before(nowTime)
                                        .and(openingTime.closeTime.after(nowTime))
                                        .and(
                                                openingTime.breakOpen.isNull()
                                                        .or(openingTime.breakClose.isNull()
                                                                .or(openingTime.breakOpen.after(nowTime)
                                                                        .or(openingTime.breakClose.before(nowTime))))
                                        )
                        )
                )
                .exists();
    }

    private  String getKoreanDay() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        Day day = Day.valueOf(String.valueOf(dayOfWeek));
        return day.getValue();
    }

    @Override
    public Restaurant findAllByRestaurantId(Long restaurantId) {
        QRestaurant restaurant = QRestaurant.restaurant;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(restaurant)
                .where(restaurant.restaurantId.eq(restaurantId))
                .fetchOne()).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }

    @Override
    public RestaurantIntroduction findRestaurantIntroductionByRestaurantId(Long restaurantId) {
        QRestaurant restaurant = QRestaurant.restaurant;
        QReview review = QReview.review;
        QOpeningTime openingTime = QOpeningTime.openingTime;

        String koreanDay = getKoreanDay();


        return jpaQueryFactory
                .select(new QRestaurantIntroduction(
                        restaurant.restaurantName,
                        reviewCount(review, restaurantId),
                        avgReviewStar(review),
                        restaurant.restaurantThumbnail.coalesce(""),
                        restaurant.category.coalesce(""),
                        isServicing(openingTime, getKoreanDay(), LocalTime.now(), restaurantId),
                        restaurant.restaurantExplanation.coalesce("")
                ))
                .from(restaurant)
                .leftJoin(review).on(restaurant.restaurantId.eq(review.restaurant.restaurantId))
                .leftJoin(openingTime)
                .on(restaurant.restaurantId.eq(openingTime.restaurant.restaurantId)
                        .and(openingTime.day.eq(koreanDay)))
                .where(restaurant.restaurantId.eq(restaurantId))
                .fetchOne();
    }

    @Override
    public List<OpenTime> findOpeningTimeByRestaurantId(Long restaurantId) {
        QOpeningTime openingTime = QOpeningTime.openingTime;
        return jpaQueryFactory
                .select(new QOpenTime(
                        openingTime.day,
                        openingTime.openTime,
                        openingTime.closeTime,
                        openingTime.breakOpen,
                        openingTime.breakClose
                ))
                .from(openingTime)
                .where(openingTime.restaurant.restaurantId.eq(restaurantId))
                .fetch();
    }
}