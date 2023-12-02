package HIM.project.controller;

import HIM.project.common.ResponseDto;
import HIM.project.service.NaverOcrApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NaverOcrApiController {



    private final NaverOcrApiService naverService;
    @PostMapping("/naver/request")
    public ResponseDto<?> checkValidation(@RequestPart(name = "file") MultipartFile file){
        return naverService.requestImage(file);
    }


}
