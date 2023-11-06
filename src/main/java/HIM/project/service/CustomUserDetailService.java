package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.entity.User;
import HIM.project.exception.CustomException;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@RequiredArgsConstructor
@Configuration
@Service

public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user  = userRepository.findAllByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return  CustomUserDetail.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }
}
