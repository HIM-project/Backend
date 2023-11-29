package HIM.project.respository;

import HIM.project.common.ErrorCode;
import HIM.project.entity.Menu;
import HIM.project.entity.QMenu;
import HIM.project.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.util.Optional;


@AllArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Menu> findAllByMenuIdAndIsDeletedIsFalse(Long menuId) {
        QMenu menu = QMenu.menu;
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(menu)
                .where(menu.isDeleted.eq(false).and(menu.menuId.eq(menuId)))
                .stream().findAny().orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND)));
    }
}
