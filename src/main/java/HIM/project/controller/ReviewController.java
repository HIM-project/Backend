package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/request/permission")
    public ResponseDto<?> getReviewPermission(@RequestPart(name = "file") MultipartFile file , @AuthUser Long userId){
        return reviewService.getReviewPermission(file,userId);
    }
}
