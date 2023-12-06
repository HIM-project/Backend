package HIM.project.respository;

import HIM.project.common.ErrorCode;
import HIM.project.dto.response.QRestaurantMenu;
import HIM.project.dto.response.RestaurantMenu;
import HIM.project.entity.Menu;
import HIM.project.entity.QMenu;
import HIM.project.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Menu> findAllByMenuIdAndIsDeletedIsFalse(Long menuId) {
        QMenu menu = QMenu.menu;
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(menu)
                .where(menu.isDeleted.eq(false).and(menu.menuId.eq(menuId)))
                .stream().findAny().orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND)));
    }

    @Override
    public List<RestaurantMenu> findAllMenuByRestaurantIdAndIsDeletedIsFalseAndOrderBySortMenu(Long restaurantId) {
        QMenu menu = QMenu.menu;
        return Optional.ofNullable(jpaQueryFactory
                .select(new QRestaurantMenu(
                        menu.foodThumbnail,
                        menu.foodName,
                        menu.price
                ))
                .from(menu)
                .where(menu.isDeleted.eq(false).and(menu.restaurant.restaurantId.eq(restaurantId)))
                .orderBy(menu.sortNumber.asc())
                .fetch()
        ).orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
    }
}
