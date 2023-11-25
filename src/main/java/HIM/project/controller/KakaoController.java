package HIM.project.controller;


import HIM.project.dto.kakao.OAuthToken;
import HIM.project.entity.User;
import HIM.project.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;



    @GetMapping("login/oauth2/code/kakao")
    public String kakaoCallback(@RequestParam(name = "code") String code , HttpServletResponse response){

        OAuthToken oAuthToken = kakaoService.createOAuthToken(code);
        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        RestTemplate rt2 = new RestTemplate();

        headers2.add("Authorization","Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        if (kakaoService.checkUser(headers2,rt2)) {
            User user = kakaoService.createOAuthMember(headers2, rt2);
            kakaoService.OAuthLoginMember(user,response);
        }else{
            User user = kakaoService.returnMember(headers2, rt2);
            kakaoService.OAuthLoginMember(user,response);
        }
        return "index";
    }
}
