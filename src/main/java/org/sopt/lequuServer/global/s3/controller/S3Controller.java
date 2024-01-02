package org.sopt.lequuServer.global.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.s3.dto.PreSignedUrlResponse;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.PRESIGNED_URL_SUCCESS;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.POSTIT_BACKGROUND_IMAGE_FOLDER_NAME;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "S3", description = "S3 API Document")
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/rolling_paper")
    @Operation(summary = "롤링페이퍼 최애 사진 업로드 Presigned URL 획득 API", description = "롤링페이퍼 최애 사진 업로드를 위한 Presigned URL 획득합니다.")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlRollingPaper() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME.getValue()));
    }

    @GetMapping("/postit")
    @Operation(summary = "포스티잇 배경 사진 업로드 Presigned URL 획득 API", description = "포스티잇 배경 사진 업로드를 위한 Presigned URL 획득합니다.")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlPostit() {
        return ApiResponse.success(PRESIGNED_URL_SUCCESS, s3Service.getUploadPreSignedUrl(POSTIT_BACKGROUND_IMAGE_FOLDER_NAME.getValue()));
    }
}
