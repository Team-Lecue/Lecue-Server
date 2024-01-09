package org.sopt.lequuServer.global.s3.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.s3.dto.PreSignedUrlResponse;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.*;

/**
 * Presigned URL을 얻기 위한 API들
 * 롤링페이퍼 최애 사진 업로드에 쓰일 용도인지,
 * 포스티잇 배경 사진 업로드에 쓰일 용도인지에 따라 다른 URL 반환
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/book")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlBook() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(BOOK_FAVORITE_IMAGE_FOLDER_NAME.getValue()));
    }

    @GetMapping("/note")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlNote() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(NOTE_BACKGROUND_IMAGE_FOLDER_NAME.getValue()));
    }

    @GetMapping("/sticker")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlSticker() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(STICKER_IMAGE_FOLDER_NAME.getValue()));
    }
}
