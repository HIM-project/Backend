package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestHeader(name = "Authorization") String accessToken , HttpServletResponse response){
        return jwtTokenProvider.reissuance(accessToken, response);
    }
}
