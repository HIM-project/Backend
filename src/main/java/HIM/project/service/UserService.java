package HIM.project.service;


import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.entity.User;
import HIM.project.exception.CustomException;
import HIM.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public ResponseDto<?> PatchMyUserInfo(String nickName, Long userId) {
        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User patchUser = User.of(user, nickName);
        userRepository.save(patchUser);
        return ResponseDto.success("데이터를 성공적으로 저장하였습니다");
    }

    public ResponseDto<?> PatchMyThumbnail(MultipartFile file, Long userId) {

        User user = userRepository.findAllByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        s3Service.deleteFile(user.getUserThumbnail());
        String uploadImageFile = s3Service.uploadImageFile(file);
        User patchThumbnailUser = User.MyThumbnailOf(user, uploadImageFile);
        userRepository.save(patchThumbnailUser);

        return ResponseDto.success(uploadImageFile);

    }
}
