package org.sopt.lequuServer.global.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.s3.dto.PreSignedUrlResponse;

@Tag(name = "S3", description = "S3관련 API")
public interface S3Api {

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Presigned Url이 생성되어 성공적으로 반환되었습니다.",
            content = @Content(schema = @Schema(implementation = PreSignedUrlResponse.class))
    )
    @Operation(summary = "레큐북 PreSignedUrl 가져오기")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlBook();

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Presigned Url이 생성되어 성공적으로 반환되었습니다.",
            content = @Content(schema = @Schema(implementation = PreSignedUrlResponse.class))
    )
    @Operation(summary = "레큐노트 PreSignedUrl 가져오기")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlNote();

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Presigned Url이 생성되어 성공적으로 반환되었습니다.",
            content = @Content(schema = @Schema(implementation = PreSignedUrlResponse.class))
    )
    @Operation(summary = "스티커 PreSignedUrl 가져오기")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrlSticker();
}
