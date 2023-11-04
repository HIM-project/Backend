package HIM.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString
public class KakaoUrlDto {
    @Value("${spring.security.oauth2.client.registration.kakao.client_id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.client_secret}")
    private String client_secret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect_uri}")
    private String redirect_uri;


    @Value("${spring.security.oauth2.client.provider.kakao.authorizationUri}")
    private String authorization_uri;
    @Value("${spring.security.oauth2.client.provider.kakao.tokenUri}")
    private String token_uri;
    @Value("${spring.security.oauth2.client.provider.kakao.userInfoUri}")
    private String userInfo_uri;
}
