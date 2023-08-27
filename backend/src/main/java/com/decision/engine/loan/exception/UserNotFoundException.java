package com.decision.engine.loan.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String NOT_FOUND = "User not found with identifier: %s";

    public UserNotFoundException(String identifier) {
        super(String.format(NOT_FOUND, identifier));
    }
}