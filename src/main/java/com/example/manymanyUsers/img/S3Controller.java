package com.example.manymanyUsers.img;

import com.example.manymanyUsers.img.dto.ImageUpload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
@Tag(name = "image", description = "S3 업로드 api")
public class S3Controller {
    private final S3Uploader s3Uploader;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 메서드 params 이름으로 images 로 설정해주셔야 합니다.")
    @Parameters({
            @Parameter(name = "images", description = "이미지 파일", example = "image1.jpg")
    })
    @PostMapping("/upload")
    public ResponseEntity updateUserImage(
            @RequestParam("images") MultipartFile multipartFile) {
        try {
            String uploadedUrl = s3Uploader.uploadFiles(multipartFile, "static");
            ImageUpload imageUpload = new ImageUpload(uploadedUrl, "이미지 업로드에 성공했습니다.");
            return new ResponseEntity(imageUpload ,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e = " + e);
            return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
        }
    }
}
