package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.dto.RegisterDto;
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
    public ResponseDto<?> registerRestaurant(@RequestParam("file") MultipartFile file){
        return  restaurantService.registerThumbnail(file);
    }
}
