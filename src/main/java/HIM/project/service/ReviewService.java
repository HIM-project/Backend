package HIM.project.service;


import HIM.project.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    public ResponseDto<?> getReviewPermission(MultipartFile file, Long userId){
        return null;
    }
}
