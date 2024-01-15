package org.sopt.lequuServer.global.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreSignedUrlResponse {

    @Schema(description = "반환된 Presigned URL로 PUT 하면 저장될 이미지 제목", example = "60c49750-0b2f-4fad-b340-9ffbb0074ebf.jpg")
    private String fileName;

    @Schema(description = "PUT 해야하는 Presigned URL", example = "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/60c49750-0b2f-4fad-b340-9ffbb0074ebf.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240109T033832Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA522AYZTVU66QK4A2%2F20240109%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=eb628c3d15f9056b82436ff73dd8714071fa43ad97b0073ef0ca182d5ee074c2")
    private String url;

    public static PreSignedUrlResponse of(String fileName, String url) {
        return new PreSignedUrlResponse(fileName, url);
    }
}