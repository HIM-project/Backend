package HIM.project.service;

import HIM.project.common.ErrorCode;
import HIM.project.exception.CustomException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of(".png", ".jpg", ".jpeg");

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private void validateFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (validateImageExtension(extension)) return;

            throw new CustomException(ErrorCode.IMAGE_EXTENSION_BAD_REQUEST);
        } catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.IMAGE_INVALID_FORMAT);
        }
    }
    public void deleteFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            String filename = path.substring(path.lastIndexOf('/') + 1);

            amazonS3Client.deleteObject(bucket, filename);

        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public String uploadImageFile(MultipartFile file) {
        if (!validateImageFile(file)) throw new CustomException(ErrorCode.IMAGE_INVALID_FORMAT);
        return upload(file);
    }

    private boolean validateImageFile(MultipartFile file) {
        return validateImageExtension(parseExtension(file));
    }

    private boolean validateImageFiles(Collection<MultipartFile> files) {
        Objects.requireNonNull(files);

        return files.stream()
                .map(this::parseExtension)
                .allMatch(this::validateImageExtension);
    }

    private String parseExtension(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private Boolean validateImageExtension(String extension) {
        return ALLOWED_IMAGE_EXTENSIONS.contains(extension);
    }

    private String upload(MultipartFile file)  {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(file.getContentType());
        meta.setContentLength(file.getSize());

        String fileName = file.getOriginalFilename();

        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), meta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
