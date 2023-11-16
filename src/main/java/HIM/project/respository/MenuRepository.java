package HIM.project.respository;

import HIM.project.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,String> {
}
