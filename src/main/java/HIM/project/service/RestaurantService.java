package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.MyRestaurant;
import HIM.project.dto.OpeningDtoList;
import HIM.project.dto.RegisterDto;
import HIM.project.entity.OpeningTime;
import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import HIM.project.entity.type.Day;
import HIM.project.exception.CustomException;
import HIM.project.respository.OpeningTimeRepository;
import HIM.project.respository.RestaurantRepository;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final OpeningTimeRepository openingTimeRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final S3Service s3Service;


    public ResponseDto<?> registerRestaurant(RegisterDto registerDto,Long userId){

        if (restaurantRepository.existsAllByCrNumber(registerDto.getCrNumber())){
            throw new CustomException(ErrorCode.RESTAURANT_HAS_BEEN_REGISTERED);
        }

        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = Restaurant.of(registerDto,user);
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
        try {
            String uploadImageFileURL = s3Service.uploadImageFile(file);
            return ResponseDto.success(uploadImageFileURL);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseDto.fail("파일 업로드에 실패하였습니다");
    }

    public ResponseDto<?> getMyRestaurant(Long userId) {
        List<Restaurant> restaurant = restaurantRepository.findAllByUserUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<MyRestaurant> responseRestaurant = new ArrayList<>();
        for (Restaurant restaurants : restaurant) {
            MyRestaurant from = MyRestaurant.from(restaurants);
            responseRestaurant.add(from);
        }
        return ResponseDto.success(responseRestaurant);
    }

    public ResponseDto<?> postMyRestaurantOpening(OpeningDtoList openingDtoList) {
        List<OpeningDtoList.OpeningDto> openingDtoListItems = openingDtoList.getOpeningDtoList();

        Restaurant restaurant = restaurantRepository.findAllByRestaurantId(openingDtoListItems.get(0).getRestaurantId()).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        List<OpeningTime> openingTimes = openingTimeRepository.findAllByRestaurant(restaurant);

        openingDtoListItems.forEach(dto -> {
            openingTimes.forEach(openingTime -> {
                if (Objects.equals(dto.getDay().getValue(), openingTime.getDay())) {
                    openingTime.setOpeingTime(dto.getOpen());
                    openingTime.setCloseTime(dto.getClose());
                }
            });
        });
        return ResponseDto.success(openingDtoList);
        }
    }
