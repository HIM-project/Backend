package HIM.project.properties;

import HIM.project.dto.kakao.KakaoUrlDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {
    KakaoUrlDto kakaoUrlDto;

    public PropertiesTest(KakaoUrlDto kakaoUrlDto) {
        this.kakaoUrlDto = kakaoUrlDto;
    }

    @Test
    void LoadProperties(){
        System.out.println("kakaoUrlDto = " + kakaoUrlDto.toString());
    }
}
