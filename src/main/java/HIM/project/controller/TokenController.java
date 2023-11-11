package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.service.RedisService;
import HIM.project.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;


    final Long ExpireAccessTokenTime =  1000L * 60 * 60;

    @Operation(summary = "AccessToken 재발급")
    @GetMapping("/reissuance")
    public ResponseEntity<?> refreshToken(@RequestHeader(name = "Authorization") String accessToken , HttpServletResponse response){
        return jwtTokenProvider.reissuance(accessToken, response);
    }

    @Operation(summary = "로그아웃 로직")
    @DeleteMapping("/logout")
    public ResponseDto<?> deleteRefreshToken(@RequestHeader(name = "Authorization") String accessToken) {
        String substring = accessToken.substring(7);
        Long userId = jwtTokenProvider.getUserId(substring);
        String values = redisService.getValues(substring);
        if (values == null) {
            redisService.setValues(accessToken,ExpireAccessTokenTime);
        }
        if (!redisService.deleteDictionary(String.valueOf(userId))){
            return ResponseDto.fail("이미 삭제된 토큰입니다");
        }
        return ResponseDto.success("성공적으로 로그아웃하였습니다");
    }
}
