package HIM.project.respository;

import HIM.project.entity.Menu;

import java.util.Optional;

public interface MenuRepositoryCustom {
    Optional<Menu> findAllByMenuIdAndIsDeletedIsFalse(Long menuId);
}
