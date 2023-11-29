package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PatchMapping("my/userinfo")
    @Operation(summary = "유저 닉네임 변경")
    public ResponseDto<?> patchMyUserInfo(@RequestParam(name = "nickname") String nickName, @AuthUser Long userId){
        return userService.PatchMyUserInfo(nickName,userId);
    }
    @PatchMapping("my/thumbnail")
    @Operation(summary = "유저 프로필 변경")
    public ResponseDto<?> patchMyUserThumbnail(@RequestParam(name = "file") MultipartFile file,@AuthUser Long userId){
        return  userService.PatchMyThumbnail(file,userId);
    }
}
