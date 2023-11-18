package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.dto.MenuDto;
import HIM.project.security.argumentreoslver.AuthUser;
import HIM.project.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;
    @PostMapping("register/menu")
    @Operation(summary = "메뉴 등록")
    public ResponseDto<?> registrationMenu(@AuthUser Long userId, @RequestPart(name = "key") List<MenuDto> menuDto, @RequestPart(name = "file")List<MultipartFile> file){
        return menuService.uploadMenu(menuDto,userId,file);
    }
}
