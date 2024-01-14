package org.sopt.lequuServer.global.s3.test;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Hidden
@RestController
@RequestMapping("/api/test/s3")
@RequiredArgsConstructor
public class TestController {

    private final TestService bookService;

    // MultipartFile 기반 이미지 업로드 (서버에 직접 이미지를 전송하는 경우 사용)
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestPart MultipartFile image, TestCreateRequest request) {
        URI location = URI.create("/test/s3" + bookService.create(request, image));
        return ResponseEntity.created(location).build();
    }

    // Presigned URL 기반 이미지 업로드 (서버에는 이미지명 String만 넘어옴)
    @PostMapping("/pre_signed")
    public ResponseEntity<Void> createPostWithPreSignedUrl(@RequestBody TestCreateImageRequest request) {
        URI location = URI.create("/test/s3/pre_signed" + bookService.createV2(request));
        return ResponseEntity.created(location).build();
    }

    // 롤링페이퍼 삭제시 업로드된 이미지도 함께 삭제
    @DeleteMapping("/{book_id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long book_id) {
        bookService.deleteById(book_id);
        return ResponseEntity.noContent().build();
    }
}
