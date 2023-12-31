package org.sopt.lequuServer.global.s3.test;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.global.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RollingPaperServiceTest {

    private static final String POST_IMAGE_FOLDER_NAME = "posts/";

    private final RollingPaperJpaRepositoryTest rollingPaperJpaRepository;
    private final S3Service s3Service;

    @Transactional
    public String create(RollingPaperCreateRequest request, MultipartFile image) {
        try {
            final String imageUrl = s3Service.uploadImage(POST_IMAGE_FOLDER_NAME, image);

            RollingPaper rollingPaper = rollingPaperJpaRepository.save(
                    RollingPaper.of(imageUrl, request.title()));

            return rollingPaper.getId().toString();

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

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
