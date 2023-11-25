package HIM.project.controller;


import HIM.project.common.ResponseDto;
import HIM.project.dto.request.MenuDto;
import HIM.project.dto.request.PatchMenuDto;
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
    //TODO : 현재 userId 를 통해서 가게를 찾는데 가게가 여러개 등록되어있을 경우에는 어떻게 찾아야하나?
    @PostMapping("register/menu")
    @Operation(summary = "메뉴 등록")
    public ResponseDto<?> registrationMenu(@AuthUser Long userId, @RequestPart(name = "dto") List<MenuDto> menuDto, @RequestPart(name = "file")List<MultipartFile> file){
        return menuService.uploadMenu(menuDto,userId,file);
    }
    @PatchMapping("patch/menu")
    @Operation(summary = "메뉴 수정")
    public ResponseDto<?> fetchMenu(@RequestPart(name = "dto") PatchMenuDto patchMenuDto,@RequestParam(name = "file") MultipartFile file){
        return menuService.patchMenu(patchMenuDto,file);
    }
}
