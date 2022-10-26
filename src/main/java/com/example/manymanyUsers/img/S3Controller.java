package com.example.manymanyUsers.img;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Tag(name = "image", description = "S3 업로드 api")
public class S3Controller {
    private final S3Uploader s3Uploader;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 메서드 params 이름으로 images 로 설정해주셔야 합니다.")
    @Parameters({
            @Parameter(name = "images", description = "이미지 파일", example = "image1.jpg")
    })
    @PostMapping("/api/image")
    public ResponseEntity<HttpStatus> updateUserImage(
            @RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이미지 업로드에 성공했습니다.", HttpStatus.OK);
    }
}
