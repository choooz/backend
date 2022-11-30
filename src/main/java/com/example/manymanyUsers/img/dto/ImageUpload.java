package com.example.manymanyUsers.img.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUpload {
    private String imageUrl;
    private String message;

    public ImageUpload(String imageUrl, String message) {
        this.imageUrl = imageUrl;
        this.message = message;
    }
}
