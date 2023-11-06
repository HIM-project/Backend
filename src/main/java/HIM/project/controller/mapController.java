package HIM.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kakao")
public class mapController {
    @GetMapping("/show-map")
    public String getMap(Model model){
        model.addAttribute("message","테스트 확인용");
        return "kakao/basic-map";
    }
}
