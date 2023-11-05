package HIM.project.controller;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.service.RedisService;
import HIM.project.service.jwt.JwtTokenProvider;
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


    @GetMapping("/reissuance")
    public ResponseEntity<?> refreshToken(@RequestHeader(name = "Authorization") String accessToken , HttpServletResponse response){
        return jwtTokenProvider.reissuance(accessToken, response);
    }

    @DeleteMapping("/logout")
    public ResponseDto<?> deleteRefreshToken(@RequestHeader(name = "Authorization") String accessToken) {
        Long userId = jwtTokenProvider.getUserId(accessToken);
        redisService.deleteDictionary(String.valueOf(userId));
        return ResponseDto.success("성공적으로 제거하였습니다");
    }
}
