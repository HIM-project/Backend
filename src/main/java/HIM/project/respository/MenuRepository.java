package HIM.project.respository;

import HIM.project.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,String> {
    Optional<Menu> findAllByMenuIdAndIsDeletedIsFalse(Long menuId);
}
