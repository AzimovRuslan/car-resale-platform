package com.example.platform.aspect.exception;

public class NoSuchRecordException extends RuntimeException{
    public NoSuchRecordException(String message) {
        super(message);
    }
}
