package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.request.MenuDto;
import HIM.project.dto.request.PatchMenuDto;
import HIM.project.entity.Menu;
import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import HIM.project.exception.CustomException;
import HIM.project.respository.MenuRepository;
import HIM.project.respository.RestaurantRepository;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {

    private final S3Service s3Service;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final MenuRepository menuRepository;
    public ResponseDto<?> uploadMenu(List<MenuDto> menuDto, Long userId, List<MultipartFile> file) {

        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findAllByUser(user).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));


        List<Menu> menus = IntStream.range(0, menuDto.size())
                .mapToObj(i -> { return Menu.form(menuDto.get(i), restaurant, s3Service.uploadImageFile(file.get(i)));
                })
                .collect(Collectors.toList());
        menuRepository.saveAll(menus);
        return ResponseDto.success("성공적으로 저장되었습니다");
    }

    public ResponseDto<?> patchMenu(PatchMenuDto patchMenuDto,MultipartFile file) {
            String menuThumbnail = s3Service.uploadImageFile(file);
            Menu menu = menuRepository.findAllByMenuIdAndIsDeletedIsFalse(patchMenuDto.getMenuId()).orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
            s3Service.deleteFile(menu.getFoodThumbnail());
            menu.applyPatch(patchMenuDto,menuThumbnail);
        return ResponseDto.success(menu);
    }
}
