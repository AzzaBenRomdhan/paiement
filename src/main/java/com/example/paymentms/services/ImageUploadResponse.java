package com.example.paymentms.services;

public class ImageUploadResponse {
    private String message;

    public ImageUploadResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
