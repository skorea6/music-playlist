package com.example.musicplaylist.common.dto;

import com.example.musicplaylist.common.status.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private int statusCode = ResultCode.SUCCESS.getStatusCode();
    private String statusMessage = ResultCode.SUCCESS.getMessage();
    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(int statusCode, String statusMessage, T data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public BaseResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public BaseResponse(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
