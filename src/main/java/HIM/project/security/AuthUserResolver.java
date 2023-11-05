package HIM.project.security;

import HIM.project.exception.CustomException;
import HIM.project.common.ErrorCode;
import HIM.project.dto.RegisterDto;
import HIM.project.entity.User;
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

    private static final String USER_ID = "userId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(AuthUser.class);
        boolean equals = parameter.getParameterType().equals(RegisterDto.class);
        return  hasParameterAnnotation&&equals;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        final Long userId = (Long)webRequest.getAttribute(USER_ID,webRequest.SCOPE_SESSION);
        log.info("resolver userId = {}",userId);
        if (userId != null){
            return User.builder().userId(userId).build();
        }else{
            throw new CustomException(ErrorCode.LOGIN_NOT_FOUND_EMAIL);
        }
    }
}
