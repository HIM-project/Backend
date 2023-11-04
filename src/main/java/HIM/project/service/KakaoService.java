package HIM.project.service;

import HIM.project.common.CustomException;
import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.KakaoProfileDto;
import HIM.project.dto.KakaoUrlDto;
import HIM.project.dto.OAuthToken;
import HIM.project.entity.RefreshToken;
import HIM.project.entity.User;
import HIM.project.respository.RefreshTokenRepository;
import HIM.project.respository.UserRepository;
import HIM.project.service.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class KakaoService {

    private final RedisService redisService;

    private final UserRepository userRepository;

    private final KakaoUrlDto kakaoUrlDto;

    private final JwtTokenProvider jwtTokenProvider;


    private final RefreshTokenRepository refreshTokenRepository;


    public OAuthToken createOAuthToken(String code) {

        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoUrlDto.getClient_id());
        params.add("redirect_uri", kakaoUrlDto.getRedirect_uri());
        params.add("code", code);
        params.add("client_secret", kakaoUrlDto.getClient_secret());
        //Header와 Body를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);



        ResponseEntity<String> response = rt.exchange(
                kakaoUrlDto.getToken_uri(),
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );


        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return oAuthToken;
    }
    public User createOAuthMember(HttpHeaders headers2, RestTemplate rt2) {
        //Header와 Bod를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                kakaoUrlDto.getUserInfo_uri(),
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(),KakaoProfileDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 필요한거 닉네임, 이메일, 주소, 이미지, 휴대폰 전화번호


        User user = User.builder()
                    .userName(kakaoProfile.kakao_account.profile.nickname)
                    .userThumbnail(kakaoProfile.kakao_account.profile.profile_image_url)
                    .email(kakaoProfile.kakao_account.email)
                    .build();

            userRepository.save(user);
        return user;
    }
    public Boolean checkUser(HttpHeaders headers2, RestTemplate rt2) {
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                kakaoUrlDto.getUserInfo_uri(),
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(),KakaoProfileDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        boolean emailCheck = userRepository.existsUserByEmail(kakaoProfile.getKakao_account().getEmail());
        if (emailCheck) {
            return false;
        }else {
            return true;
        }
    }
    public User returnMember(HttpHeaders headers2, RestTemplate rt2) {
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                kakaoUrlDto.getUserInfo_uri(),
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(),KakaoProfileDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        User user = userRepository.findAllByEmail(kakaoProfile.kakao_account.email).orElseThrow();
        return user;

    }

    public ResponseDto<?> OAuthLoginMember(User user, HttpServletResponse response) {

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        RefreshToken token = refreshTokenRepository.findByUserId(String.valueOf(user.getUserId()));
        if (token == null){
            redisService.setValues(refreshToken, String.valueOf(user.getUserId()));
        }
        response.setHeader("Authorization",accessToken);
        return ResponseDto.success("로그인에 성공하였습니다.");
    }
}

