package com.example.boot.entity.response;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class CommonResponse {
    private boolean success;
    private String message;

    public CommonResponse() {
    }

    public CommonResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
