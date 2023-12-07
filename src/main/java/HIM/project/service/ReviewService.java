package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.response.NaverOcrDto;
import HIM.project.entity.Restaurant;
import HIM.project.exception.CustomException;
import HIM.project.respository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final NaverOcrApiService naverService;
    private final RedisService redisService;
    private final RestaurantRepository restaurantRepository;
    public ResponseDto<?> getReviewPermission(MultipartFile file, Long userId,Long restaurantId ){
        NaverOcrDto naverOcrDto = naverService.requestImage(file);
        String date = naverOcrDto.getImages().get(0).getReceipt().getResult().getPaymentInfo().getTime().getText();
        String value = naverOcrDto.getImages().get(0).getReceipt().getResult().getStoreInfo().getBizNum().getFormatted().getValue();
        String confirmNum = userId + "_" + date;
        String confirmTime = redisService.getConfirmTime(confirmNum);
        if (confirmTime != null){
            throw new CustomException(ErrorCode.BILLING_HAS_BEEN_REGISTERED);
        }
        redisService.setConfirmNum(confirmNum, date);
        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId).orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        if (!restaurant.getCrNumber().matches(value)){
            throw new CustomException(ErrorCode.BILLING_HAS_BEEN_REGISTERED);
        }
        redisService.setUserPermission(userId+"Permission",String.valueOf(true));
        return ResponseDto.success(naverOcrDto.getImages().get(0).getInferResult());
    }
}
