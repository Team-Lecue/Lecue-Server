package org.sopt.lequuServer.global.s3.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/test/s3")
@RequiredArgsConstructor
public class TestController {

    private final TestService rollingPaperService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestPart MultipartFile image, TestCreateRequest request) {
        URI location = URI.create("/test/s3" + rollingPaperService.create(request, image));
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/pre_signed")
    public ResponseEntity<Void> createPostWithPreSignedUrl(@RequestBody TestCreateImageRequest request) {
        URI location = URI.create("/test/s3/pre_signed" + rollingPaperService.createV2(request));
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{rolling_paper_id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long rolling_paper_id) {
        rollingPaperService.deleteById(rolling_paper_id);
        return ResponseEntity.noContent().build();
    }
}
