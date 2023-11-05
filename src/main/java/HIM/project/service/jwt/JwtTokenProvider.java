package HIM.project.service.jwt;


import HIM.project.exception.CustomException;
import HIM.project.common.ErrorCode;
import HIM.project.entity.User;
import HIM.project.respository.UserRepository;
import HIM.project.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final String AuthHeader = "Authorization";
    private String secretKey = "Super-Coding";

    final private UserDetailsService userDetailsService;


    private final RedisService redisService;

    private final UserRepository userRepository;

    final long AccessTokenValidMilliSecond =  1000L * 60 * 60; // 1시간
    final long RefreshTokenValidMilliSecond = 1000L *60 * 60 *24 * 7; //  1주일

    @PostConstruct
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AuthHeader);
        return token==null ? null : token.substring(7);
    }

    public Long getUserId(String jwtToken){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }
    public String createAccessToken(User user){
        Claims claims = Jwts.claims().setSubject("userAccessToken");
        claims.put("userId" ,user.getUserId());
        claims.put("email",user.getEmail());
        claims.put("nickname",user.getUserName());

        Date now = new Date();

        return Jwts.builder()
                .setIssuer("HIM")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ AccessTokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String createRefreshToken(User user){
        Claims claims = Jwts.claims().setSubject("userRefreshToken");
        claims.put("userId" ,user.getUserId());
        claims.put("email",user.getEmail());
        claims.put("username",user.getUserName());

        Date now = new Date();

        return Jwts.builder()
                .setIssuer("HIM")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ RefreshTokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validToken(String jwtToken){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        }catch (Exception e){
            return false;
        }
    }
    public Authentication getAuthentication(String jwtToken){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails,
                "",
                userDetails.getAuthorities());
    }


    public String getEmail(String jwtToken){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("email",String.class);
    }

    public String timestampToString(Instant instant){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        if(instant != null)return formatter.format(instant);

        else return null;
    }

    public ResponseEntity<?> reissuance(String accessToken,HttpServletResponse response) {

        Long userId = getUserId(accessToken);

        String refreshToken = redisService.getValues(String.valueOf(userId));

        if (refreshToken == null){
            throw new CustomException(ErrorCode.ACCESSTOKEN_NOT_MATCH);
        }
        User user = userRepository.findAllByUserId(getUserId(refreshToken)).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String token = createAccessToken(user);

        response.setHeader(AuthHeader,token);

        return ResponseEntity.ok("재발급에 성공하였습니다");
    }
}

