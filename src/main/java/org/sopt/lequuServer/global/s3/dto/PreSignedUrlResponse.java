package org.sopt.lequuServer.global.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreSignedUrlResponse {
    private String fileName;
    private String url;

    public static PreSignedUrlResponse of(String fileName, String url) {
        return new PreSignedUrlResponse(fileName, url);
    }
}