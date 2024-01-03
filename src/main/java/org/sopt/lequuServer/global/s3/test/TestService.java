package org.sopt.lequuServer.global.s3.test;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestJpaRepository rollingPaperJpaRepository;
    private final S3Service s3Service;

    // MultipartFile 기반 이미지 업로드 (서버에 직접 이미지를 전송하는 경우 사용)
    @Transactional
    public String create(TestCreateRequest request, MultipartFile image) {
        try {
            final String imageUrl = s3Service.uploadImage(ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME.getValue(), image);

            RollingPaper rollingPaper = rollingPaperJpaRepository.save(RollingPaper.test(imageUrl, request.title()));

            return rollingPaper.getId().toString();

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Presigned URL 기반 이미지 업로드 (서버에는 이미지명 String만 넘어옴)
    @Transactional
    public String createV2(TestCreateImageRequest request) {
        try {
            String imageUrl = s3Service.getURL(ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME.getValue() + request.fileName());

            RollingPaper rollingPaper = rollingPaperJpaRepository.save(RollingPaper.test(imageUrl, request.title()));

            return rollingPaper.getId().toString();

        } catch (RuntimeException e) {
            // 게시글 저장에 실패 할 시 S3에 미리 업로드 되어 있던 이미지를 삭제
            try {
                s3Service.deleteImage(ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME.getValue() + "/" + request.fileName());
            } catch (IOException io) {
                // 이미지 삭제 과정에서 에러가 발생할 경우
                throw new RuntimeException("S3에서 이미지를 삭제하는 데에 실패했습니다: " + io.getMessage());
            }
            throw e;
        }
    }

    // 롤링페이퍼 삭제시 업로드된 이미지도 함께 삭제
    @Transactional
    public void deleteById(Long rolling_paper_id) {
        try {
            RollingPaper rollingPaper = rollingPaperJpaRepository.findById(rolling_paper_id)
                    .orElseThrow(() -> new RuntimeException("해당하는 롤링페이퍼가 없습니다."));

            s3Service.deleteImage(rollingPaper.getFavoriteImage());
            rollingPaperJpaRepository.deleteById(rolling_paper_id);

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
