package com.example.manymanyUsers.img;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/image")
    public ResponseEntity<HttpStatus> updateUserImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이미지 업로드에 성공했습니다.", HttpStatus.OK);
    }
}
