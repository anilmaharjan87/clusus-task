package com.clusus.exceptions;

public class DealAlreadyExistException extends RuntimeException {
    public DealAlreadyExistException(String message) {
        super(message);
    }
}
