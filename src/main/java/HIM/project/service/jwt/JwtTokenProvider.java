package HIM.project.service.jwt;


import HIM.project.dto.KakaoProfileDto;
import HIM.project.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private String secretKey = "Super-Coding";

    final private UserDetailsService userDetailsService;

    final long AccessTokenValidMilliSecond =  1000L * 60 * 60; // 1시간
    final long RefreshTokenValidMilliSecond = 1000L *60 * 60 *24 * 7; //  1주일

    @PostConstruct
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token==null ? null : token.substring(7);
    }

    public Long getUserId(String jwtToken){
        log.info("getMemberIdx jwtToken {}" ,jwtToken);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody();
        Long userId = (Long) claims.get("user_id");
        return userId;
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
        String email= Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("email",String.class);
        return email;
    }

    public String timestampToString(Instant instant){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        if(instant != null)return formatter.format(instant);

        else return null;
    }

}

