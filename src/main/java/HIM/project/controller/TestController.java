package HIM.project.controller;


import HIM.project.security.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TestController {
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
}
