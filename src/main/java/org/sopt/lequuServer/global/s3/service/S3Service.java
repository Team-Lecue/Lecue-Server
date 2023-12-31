package org.sopt.lequuServer.global.s3.service;

import lombok.val;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.sopt.lequuServer.global.config.AWSConfig;
import org.sopt.lequuServer.global.s3.dto.PreSignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.*;

@Component
public class S3Service {

    private final String bucketName;
    private final AWSConfig awsConfig;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/webp");
    // 파일 최대 크기 5MB
    private static final Long MAX_FILE_SIZE = 5 * 1024 * 1024L;
    // PreSigned URL 만료시간 60분
    private static final Long PRE_SIGNED_URL_EXPIRE_MINUTE = 60L;

    public S3Service(@Value("${cloud.aws.credentials.s3-bucket-name}") final String bucketName, AWSConfig awsConfig) {
        this.bucketName = bucketName;
        this.awsConfig = awsConfig;
    }

    public String uploadImage(String directoryPath, MultipartFile image) throws IOException {
        validateExtension(image);
        validateFileSize(image);

        final String key = directoryPath + generateImageFileName();
        final S3Client s3Client = awsConfig.getS3Client();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(image.getContentType())
                .contentDisposition("inline")
                .build();

        RequestBody requestBody = RequestBody.fromBytes(image.getBytes());
        s3Client.putObject(request, requestBody);
        return key;
    }

    public void deleteImage(String key) throws IOException {
        final S3Client s3Client = awsConfig.getS3Client();

        s3Client.deleteObject((DeleteObjectRequest.Builder builder) ->
                builder.bucket(bucketName)
                        .key(key)
                        .build()
        );
    }

    private String generateImageFileName() {
        return UUID.randomUUID().toString() + ".jpg";
    }

    private void validateExtension(MultipartFile image) {
        String contentType = image.getContentType();
        if (!IMAGE_EXTENSIONS.contains(contentType)) {
            throw new CustomException(IMAGE_EXTENSION_ERROR);
        }
    }

    private void validateFileSize(MultipartFile image) {
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new CustomException(IMAGE_SIZE_ERROR);
        }
    }

    public PreSignedUrlResponse getUploadPreSignedUrl(final String prefix) {
        try {
            PreSignedUrlResponse preSignedUrlResponse = getPreSignedUrl(prefix);
            return new PreSignedUrlResponse(
                    preSignedUrlResponse.getFileName(),
                    preSignedUrlResponse.getUrl()
            );
        } catch (RuntimeException e) {
            throw new CustomException(GET_UPLOAD_PRESIGNED_URL_ERROR);
        }
    }

    public PreSignedUrlResponse getPreSignedUrl(final String prefix) {
        val uuidFileName = generateImageFileName(); // val: lombok에서 제공하는 타입 추론 -> 뒤에 내용을 보고 자동으로 String이라고 타입을 추론
        val key = prefix + uuidFileName; // 경로 + 파일 이름

        S3Presigner preSigner = awsConfig.getS3Presigner();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // S3에서 업로드는 PUT 요청
        PutObjectPresignRequest preSignedUrlRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                .putObjectRequest(putObjectRequest)
                .build();
        // 조회를 하고 싶다면 GetOjbectPresignRequest
        // 삭제를 하고 싶다면 DeleteObjectPresignRequest

        URL url = preSigner.presignPutObject(preSignedUrlRequest).url();

        return PreSignedUrlResponse.of(uuidFileName, url.toString());
    }

    public String getURL(final String imageKey){
        try {
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(imageKey)
                    .build();

            S3Client s3Client = awsConfig.getS3Client();

            URL url = s3Client.utilities().getUrl(request);

            String urlWithKey = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imageKey;
            if(urlWithKey.equals(url.toString())){
                return url.toString();
            }
            throw new RuntimeException("S3에서 이미지를 불러오는 데에 실패했습니다.");
        } catch (S3Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}