package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/request/permission")
    @Operation(summary = "리뷰 권한 얻기")
    public ResponseDto<?> getReviewPermission(@RequestPart(name = "file") MultipartFile file , @RequestParam(name = "restaurantId") Long restaurantId, @AuthUser Long userId){
        return reviewService.getReviewPermission(file,userId,restaurantId);
    }
}
