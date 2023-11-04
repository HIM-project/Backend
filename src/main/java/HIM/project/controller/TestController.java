package HIM.project.controller;


import HIM.project.dto.RegisterDto;
import HIM.project.security.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String ArguTest(@AuthUser RegisterDto registerDto){
        return "테스트 입니다";
    }
}
