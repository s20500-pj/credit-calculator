package com.decision.engine.loan.validation;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.exception.DataRangeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestValidator implements Validator<LoanRequestDto> {

    int minAmount;
    int maxAmount;
    int minPeriod;
    int maxPeriod;

    public LoanRequestValidator(@Value("${loan.min.amount}") int minAmount,
                                @Value("${loan.max.amount}") int maxAmount,
                                @Value("${loan.min.period}") int minPeriod,
                                @Value("${loan.max.period}") int maxPeriod) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.minPeriod = minPeriod;
        this.maxPeriod = maxPeriod;
    }

    @Override
    public void throwIfNotValid(LoanRequestDto dto) {
        if (dto.getAmount() < minAmount || dto.getAmount() > maxAmount) {
            throw DataRangeException.ofWrongAmount(minAmount, maxAmount);
        }
        if (dto.getPeriod() < minPeriod || dto.getPeriod() > maxPeriod) {
            throw DataRangeException.ofWrongPeriod(minPeriod, maxPeriod);
        }
    }
}