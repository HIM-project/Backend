package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.dto.RegisterDto;
import HIM.project.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("registration")
    @Operation(summary = "가게 등록")
    public ResponseDto<?> registerRestaurant(@RequestBody  RegisterDto registerDto){
        return  restaurantService.registerRestaurant(registerDto);
    }

    @PostMapping("registration/thumbnail")
    @Operation(summary = "가게 썸네일 등록")
    public ResponseDto<?> registerRestaurant(@RequestParam("file") MultipartFile file){
        return  restaurantService.registerThumbnail(file);
    }
}
