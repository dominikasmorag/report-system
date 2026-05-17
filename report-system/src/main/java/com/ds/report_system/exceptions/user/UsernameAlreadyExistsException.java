package com.ds.report_system.exceptions.user;

public class UsernameAlreadyExistsException extends RuntimeException {
    private String message;

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
