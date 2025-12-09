package com.mehmetsolak.results;

import java.util.Map;

public final class Result<T> {

    private boolean isSuccess;
    private String message;
    private T data;

    private Map<String, String> validationErrors;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    private Result() { }

    private Result(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    private Result(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    private Result(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    private Result(boolean isSuccess, Map<String, String> validationErrors) {
        this.isSuccess = isSuccess;
        this.validationErrors = validationErrors;
    }

    private Result(boolean isSuccess, String message, Map<String, String> validationErrors) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    private Result(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(true, message);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> failure() {
        return new Result<>(false);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message);
    }

    public static <T> Result<T> failure(Map<String, String> validationErrors) {
        return new Result<>(false, validationErrors);
    }

    public static <T> Result<T> failure(String message, Map<String, String> validationErrors) {
        return new Result<>(false, message, validationErrors);
    }
}
