package HIM.project.controller;


import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final S3Service s3Service;
    @GetMapping("/test")
    public String TestController() {
        log.info("테스트 구동");
        return "테스트 입니다";
    }
    @GetMapping("/test2")
    public String ArguTest(@AuthUser Long userId){
        System.out.println("userId = " + userId);
        return "테스트 입니다";
    }
    @GetMapping("/login/oauth/info")
    public void loginTest(Authentication authentication,
                          @AuthenticationPrincipal UserDetails userDetails){

        Object principal = authentication.getPrincipal();
        System.out.println("principal = " + principal);
    }

    @PostMapping("/s3")
    private void getS3UrlExample(@RequestParam("file") MultipartFile file) throws IOException {

        String url = s3Service.uploadImageFile(file); // S3 Url 을 가져오는 메서드입니다.
        log.info("url = {} ",url);
        s3Service.deleteFile(url); // S3 에 저장된 파일을 삭제하는 메서드입니다.

    }
}
