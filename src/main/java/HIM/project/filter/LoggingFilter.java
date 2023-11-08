package HIM.project.filter;

import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String url = String.valueOf(request.getRequestURL());
            log.info("{} {} 요청이 들어왔습니다", method, url);
            filterChain.doFilter(request, response);
        log.info("{}  {} 가 상태 {} 로 응답이 나갑니다.",method,url,response.getStatus());
    }
}