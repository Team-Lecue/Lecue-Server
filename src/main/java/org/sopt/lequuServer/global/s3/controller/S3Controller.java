package org.sopt.lequuServer.global.s3.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.sopt.lequuServer.global.s3.dto.PreSignedUrlResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.POSTIT_BACKGROUND_IMAGE_FOLDER_NAME;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME;

/**
 * Presigned URL을 얻기 위한 API들
 * 롤링페이퍼 최애 사진 업로드에 쓰일 용도인지,
 * 포스티잇 배경 사진 업로드에 쓰일 용도인지에 따라 다른 URL 반환
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/rolling_paper")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlRollingPaper() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME.getValue()));
    }

    @GetMapping("/postit")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlPostit() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(POSTIT_BACKGROUND_IMAGE_FOLDER_NAME.getValue()));
    }
}
