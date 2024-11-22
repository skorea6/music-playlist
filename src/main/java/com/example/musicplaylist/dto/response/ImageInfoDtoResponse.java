package com.example.snapcampus.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageInfoDtoResponse {
    private String path;
    private boolean isActive;

    public ImageInfoDtoResponse(String path, boolean isActive) {
        this.path = path;
        this.isActive = isActive;
    }
}
