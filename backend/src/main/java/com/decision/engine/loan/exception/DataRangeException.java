package com.decision.engine.loan.exception;

public class DataRangeException extends RuntimeException {

    private static final String WRONG_AMOUNT = "Loan amount has to be between %d € and %d €.";
    private static final String WRONG_PERIOD = "Loan period has to be between %d and %d months.";

    public DataRangeException(String message) {
        super(message);
    }

    public static DataRangeException ofWrongAmount(int min, int max) {
        return new DataRangeException(String.format(WRONG_AMOUNT, min, max));
    }

    public static DataRangeException ofWrongPeriod(int min, int max) {
        return new DataRangeException(String.format(WRONG_PERIOD, min, max));
    }
}
