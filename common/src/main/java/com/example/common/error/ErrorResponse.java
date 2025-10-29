package com.example.common.error;

public class ErrorResponse {
    private final String message;
    private final String path;

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}


