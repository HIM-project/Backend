package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.MenuDto;
import HIM.project.entity.Menu;
import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import HIM.project.exception.CustomException;
import HIM.project.respository.MenuRepository;
import HIM.project.respository.RestaurantRepository;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequiredArgsConstructor
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
                .mapToObj(i -> {
                    try {
                        return Menu.of(menuDto.get(i), restaurant, s3Service.uploadImageFile(file.get(i)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        menuRepository.saveAll(menus);
        return ResponseDto.success("성공적으로 저장되었습니다");
    }
}
