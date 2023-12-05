package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.kakao.OpeningDtoList;
import HIM.project.dto.request.PatchRestaurantDto;
import HIM.project.dto.request.RegisterDto;
import HIM.project.dto.response.MyRestaurant;
import HIM.project.dto.response.RestaurantInfo;
import HIM.project.entity.OpeningTime;
import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import HIM.project.entity.type.Day;
import HIM.project.exception.CustomException;
import HIM.project.respository.OpeningTimeRepository;
import HIM.project.respository.RestaurantRepository;
import HIM.project.respository.RestaurantRepositoryImpl;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final OpeningTimeRepository openingTimeRepository;

    private final RestaurantRepository restaurantRepository;

    private final RestaurantRepositoryImpl restaurantRepositoryImpl;

    private final UserRepository userRepository;

    private final S3Service s3Service;


    public ResponseDto<?> registerRestaurant(RegisterDto registerDto,Long userId){

        if (restaurantRepository.existsAllByCrNumber(registerDto.getCrNumber())){
            throw new CustomException(ErrorCode.RESTAURANT_HAS_BEEN_REGISTERED);
        }

        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = Restaurant.form(registerDto,user);
        restaurantRepository.save(restaurant);

        initializeOpening(restaurant);

        return ResponseDto.success("가게 등록에 성공하였습니다.");
    }

    private void initializeOpening(Restaurant restaurant) {
        for (Day day : Day.values()){
            OpeningTime openingTime = OpeningTime.of(day, restaurant);
            openingTimeRepository.save(openingTime);
        }
    }

    public ResponseDto<?> registerThumbnail(MultipartFile file) {
            String uploadImageFileURL = s3Service.uploadImageFile(file);
            return ResponseDto.success(uploadImageFileURL);
    }

    public ResponseDto<?> getMyRestaurant(Long userId) {
        List<MyRestaurant> restaurant = restaurantRepositoryImpl.findAllByUserUserId(userId);
        return ResponseDto.success(restaurant);
    }

    public ResponseDto<?> postMyRestaurantOpening(OpeningDtoList openingDtoList) {
        List<OpeningDtoList.OpeningDto> openingDtoListItems = openingDtoList.getOpeningDtoList();

        Restaurant restaurant = restaurantRepositoryImpl.findAllByRestaurantId(openingDtoListItems.get(0).getRestaurantId());

        List<OpeningTime> openingTimes = openingTimeRepository.findAllByRestaurant(restaurant);

        openingDtoListItems.forEach(dto -> {
            openingTimes.forEach(openingTime -> {
                if (Objects.equals(dto.getDay().getValue(), openingTime.getDay())) {
                    openingTime.setOpenTime(dto.getOpen());
                    openingTime.setCloseTime(dto.getClose());
                    openingTime.setBreakOpen(dto.getBreakOpen());
                    openingTime.setBreakClose(dto.getBreakClose());
                }
            });
        });
        return ResponseDto.success(openingDtoList);
        }

    public ResponseDto<?> patchMyRestaurant(PatchRestaurantDto restaurantDto, MultipartFile file) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantDto.getRestaurantId()).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        s3Service.deleteFile(restaurant.getRestaurantThumbnail());
        String uploadImageFileURL = s3Service.uploadImageFile(file);
        restaurant.applyPatch(restaurantDto,uploadImageFileURL);
        return ResponseDto.success("성공적으로 저장하였습니다");
    }

    public ResponseDto<?> getRestaurantInfo(Long restaurantId) {
        RestaurantInfo restaurantInfo = restaurantRepositoryImpl.findByRestaurantId(restaurantId);
        return ResponseDto.success(restaurantInfo);
    }
}
