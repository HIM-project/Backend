package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.dto.kakao.OpeningDtoList;
import HIM.project.dto.request.PatchRestaurantDto;
import HIM.project.dto.request.RegisterDto;
import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("registration")
    @Operation(summary = "가게 등록")
    public ResponseDto<?> registerRestaurant(@RequestBody RegisterDto registerDto, @AuthUser Long userId){
        return  restaurantService.registerRestaurant(registerDto,userId);
    }

    @PostMapping("registration/thumbnail")
    @Operation(summary = "가게 썸네일 등록")
    public ResponseDto<?> registerRestaurant(@RequestParam(name = "file") MultipartFile file){
        return  restaurantService.registerThumbnail(file);
    }

    @GetMapping("my/restaurant")
    @Operation(summary = "내 가게 확인")
    public ResponseDto<?> myRestaurant(@AuthUser Long userId){
        return restaurantService.getMyRestaurant(userId);
    }
    @PatchMapping("my/patch/restaurant")
    @Operation(summary = "가게 소개 수정")
    public ResponseDto<?> patchMyRestaurant(@RequestPart(name = "dto") PatchRestaurantDto restaurantDto, @RequestParam(name = "file") MultipartFile file){
        return restaurantService.patchMyRestaurant(restaurantDto,file);
    }

    @PatchMapping("my/restaurant/opening")
    @Operation(summary = "가게 영업 시간 관리")
    public ResponseDto<?> myRestaurantOpening(@RequestBody OpeningDtoList openingDtoList){
        return restaurantService.postMyRestaurantOpening(openingDtoList);
    }
}
