package HIM.project.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    LOGIN_NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "등록되어 있지 않은 이메일 입니다."),
    LOGIN_NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "등록된 회원의 정보와 일치하지 않습니다."),

    REFRESHTOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 리프레쉬 토큰을 찾을 수 없습니다"),

    ACCESSTOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST,"해당 엑세스 토큰을 찾을 수 없습니다"),
    ACCESSTOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST,"해당 엑세스 토큰은 만료되었습니다"),
    ACCESSTOKEN_IS_MALFORMED(HttpStatus.FORBIDDEN,"해당 엑세스 토큰은 잘못된 토큰입니다."),
    ACCESSTOKEN_BLACKLIST(HttpStatus.BAD_REQUEST,"해당 엑세스 토큰은 블랙리스트에 등록되었습니다."),

    ACCESSTOKEN_SIGNATURE_EXCEPTION(HttpStatus.FORBIDDEN,"해당 엑세스 토큰은 변조되었습니다"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다")




    ;
    private final HttpStatus httpStatus;
    private final String errorMsg;
}


