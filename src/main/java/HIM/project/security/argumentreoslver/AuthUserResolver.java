package HIM.project.security.argumentreoslver;

import HIM.project.exception.CustomException;
import HIM.project.common.ErrorCode;
import HIM.project.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private static final String Auth  = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(AuthUser.class);
        boolean equals = parameter.getParameterType().equals(Long.class);
        return  hasParameterAnnotation&&equals;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String authorizationHeader = webRequest.getHeader(Auth);
        log.info("Authorization Header ::: " + authorizationHeader);
        if (authorizationHeader == null) {
            throw new CustomException(ErrorCode.ACCESSTOKEN_NOT_MATCH);
        }
        String jwtToken = authorizationHeader.substring(7);
        return jwtTokenProvider.getUserId(jwtToken);
    }
}
