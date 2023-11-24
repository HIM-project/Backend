package HIM.project.exception;

import HIM.project.common.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice   //@ExceptionHandler,@ModelAttribute, @InitBinder 가 적용된 메서드에 AOP 적용
public class CustomExceptionHandler {

    @ExceptionHandler(value = {SignatureException.class})
    public ResponseDto<?> handleSignatureException() {
        return ResponseDto.fail("변조된 토큰입니다");
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    public ResponseDto<?> handleMalformedJwtException() {
        return ResponseDto.fail("올바르지 않는 토큰입니다");
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseDto<?> handleExpiredJwtException() {
        return ResponseDto.fail("만료된 토큰입니다 재발급 받아주세요");    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> handleApiRequestException(CustomException ex) {
        HttpStatus httpStatus = ex.getErrorCode().getHttpStatus();
        String errMSG = ex.getErrorCode().getErrorMsg();
        return new ResponseEntity<>(ResponseDto.fail(errMSG), httpStatus);
    }

    @ExceptionHandler(value = {CustomMessageException.class})
    public ResponseEntity<?> handMessageRequestException(CustomMessageException ex) {

        HttpStatus httpStatus = ex.getErrorCode().getHttpStatus();
        String errMSG = ex.getErrorCode().getErrorMsg();
        String dynamicMSG = ex.getDynamicMessage();
        String dynamicMSG2 = ex.getDynamicMessage2();
        return new ResponseEntity<>(ResponseDto.fail(dynamicMSG + dynamicMSG2 + errMSG), httpStatus);
    }
}
