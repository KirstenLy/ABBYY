package com.example.sdk.models;

public class AbbyyError {

    public enum ErrorType{
        INTERNET_NO_AVIALABLE,
        SERVER_NO_RESPONSE
    }

    private ErrorType errorType;

    public AbbyyError(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
