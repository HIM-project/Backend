package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.RegisterDto;
import HIM.project.entity.Restaurant;
import HIM.project.entity.User;
import HIM.project.exception.CustomException;
import HIM.project.respository.RestaurantRepository;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final S3Service s3Service;

    public ResponseDto<?> registerRestaurant(RegisterDto registerDto,Long userId){
        Restaurant isRestaurant = restaurantRepository.findAllByCrNumber(registerDto.getCrNumber());
        if (isRestaurant != null){
            throw new CustomException(ErrorCode.RESTAURANT_HAS_BEEN_REGISTERED);
        }

        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = Restaurant.of(registerDto,user);
        restaurantRepository.save(restaurant);
        return ResponseDto.success("가게 등록에 성공하였습니다.");
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
}
