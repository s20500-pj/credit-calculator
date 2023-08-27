package com.decision.engine.loan.exception;

public class UserDataLoadException extends RuntimeException {

    private static final String LOAD_ERROR = "Error loading user data.";

    public UserDataLoadException() {
        super(LOAD_ERROR);
    }
}