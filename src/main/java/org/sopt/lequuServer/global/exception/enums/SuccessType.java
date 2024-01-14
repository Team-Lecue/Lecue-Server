package org.sopt.lequuServer.global.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {

    /**
     * 200 OK
     */
    PROCESS_SUCCESS(HttpStatus.OK, "OK"),
    PRESIGNED_URL_SUCCESS(HttpStatus.OK, "Presigned Url이 생성되어 성공적으로 반환되었습니다."),

    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "Access 토큰 재발급에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    KAKAO_ACCESS_TOKEN_SUCCESS(HttpStatus.OK, "카카오 엑세스 토큰을 가져오는데 성공했습니다."),
    SET_MEMBER_NICKNAME_SUCCESS(HttpStatus.OK, "유저 닉네임을 설정하여 회원가입에 성공했습니다."),

    GET_STICKER_PACK_SUCCESS(HttpStatus.OK, "스티커팩 목록 조회에 성공했습니다."),
    GET_SPLASH_SUCCESS(HttpStatus.OK, "스플래시 조회에 성공했습니다."),
    GET_HOME_SUCCESS(HttpStatus.OK, "홈 화면 조회에 성공했습니다."),
    GET_MYPAGE_BOOK_SUCCESS(HttpStatus.OK, "마이페이지의 유저 닉네임과 내 레큐북 조회에 성공했습니다."),
    GET_MYPAGE_NOTE_SUCCESS(HttpStatus.OK, "마이페이지의 유저 닉네임과 내 레큐노트 조회에 성공했습니다."),
    GET_BOOK_DETAIL_SUCCESS(HttpStatus.OK, "레큐북 상세 조회에 성공했습니다"),

    /**
     * 201 CREATED
     */
    CREATE_BOOK_SUCCESS(HttpStatus.CREATED, "레큐북이 성공적으로 생성됐습니다."),
    POST_STICKER_SUCCESS(HttpStatus.CREATED, "스티커 부착에 성공했습니다."),
    CREATE_NOTE_SUCCESS(HttpStatus.CREATED, "레큐노트를 성공적으로 생성했습니다."),

    /**
     * 204 NO CONTENT
     */
    DELETE_BOOK_SUCCESS(HttpStatus.NO_CONTENT, "레큐북을 성공적으로 삭제했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
