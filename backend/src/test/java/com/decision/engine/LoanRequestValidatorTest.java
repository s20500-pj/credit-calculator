package com.decision.engine;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.exception.DataRangeException;
import com.decision.engine.loan.validation.LoanRequestValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

public class LoanRequestValidatorTest {

    private final int minAmount = 2000;
    private final int maxAmount = 10000;
    private final int minPeriod = 12;
    private final int maxPeriod = 60;

    private final LoanRequestValidator validator = new LoanRequestValidator(minAmount, maxAmount, minPeriod, maxPeriod);

    private final String EXPECTED_AMOUNT_MESSAGE = String.format("Loan amount has to be between %d € and %d €.", minAmount, maxAmount);
    private final String EXPECTED_PERIOD_MESSAGE = String.format("Loan period has to be between %d and %d months.", minPeriod, maxPeriod);

    @Test
    public void testValidLoanRequest() {
        LoanRequestDto request = new LoanRequestDto(anyString(), 12, 2000);

        assertDoesNotThrow(() -> validator.throwIfNotValid(request));
    }

    @Test
    public void testLoanPeriodBelowMin() {
        LoanRequestDto request = new LoanRequestDto(anyString(), 10, 2000);

        Exception exception = assertThrows(DataRangeException.class, () -> validator.throwIfNotValid(request));
        assertTrue(exception.getMessage().contains(EXPECTED_PERIOD_MESSAGE));
    }

    @Test
    public void testLoanPeriodAboveMax() {
        LoanRequestDto request = new LoanRequestDto(anyString(), 100, 2000);

        Exception exception = assertThrows(DataRangeException.class, () -> validator.throwIfNotValid(request));
        assertTrue(exception.getMessage().contains(EXPECTED_PERIOD_MESSAGE));
    }

    @Test
    public void testLoanAmountBelowMin() {
        LoanRequestDto request = new LoanRequestDto(anyString(), 20, 100);

        Exception exception = assertThrows(DataRangeException.class, () -> validator.throwIfNotValid(request));
        assertTrue(exception.getMessage().contains(EXPECTED_AMOUNT_MESSAGE));
    }

    @Test
    public void testLoanAmountAboveMax() {
        LoanRequestDto request = new LoanRequestDto(anyString(), 30, 20000);

        Exception exception = assertThrows(DataRangeException.class, () -> validator.throwIfNotValid(request));
        assertTrue(exception.getMessage().contains(EXPECTED_AMOUNT_MESSAGE));
    }
}