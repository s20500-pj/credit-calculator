package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import com.decision.engine.loan.dto.PersonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private final PersonDao personDao;

    int maxAmount;
    int minPeriod;
    int maxPeriod;

    public LoanServiceImpl(PersonDao personDao,
                           @Value("${loan.max.amount}") int maxAmount,
                           @Value("${loan.min.period}") int minPeriod,
                           @Value("${loan.max.period}") int maxPeriod) {
        this.personDao = personDao;
        this.maxAmount = maxAmount;
        this.minPeriod = minPeriod;
        this.maxPeriod = maxPeriod;
    }

    @Override
    public LoanResponseDto getLoanDecision(LoanRequestDto loanRequestDto) {
        PersonDto personDto = personDao.findByIdentifier(loanRequestDto.getIdentifier());

        if (personDto.isDebt()) {
            return buildLoanResponseDto(false, 0);
        }

        int maxAmount = calculateMaxAmount(loanRequestDto.getPeriod(), personDto.getCreditModifier());
        boolean decision = isLoanApproved(maxAmount, loanRequestDto.getAmount());
        int possibleLoanAmount = getActualLoanAmount(maxAmount);

        LoanResponseDto loanResponseDto = buildLoanResponseDto(decision, possibleLoanAmount);

        if (!decision) {
            int requiredPeriod = computeRequiredPeriod(loanRequestDto.getAmount(), personDto.getCreditModifier());
            if (requiredPeriod <= this.maxPeriod && requiredPeriod >= this.minPeriod) {
                loanResponseDto.setRequiredPeriod(requiredPeriod);
            }
        }

        return loanResponseDto;
    }

    private boolean isLoanApproved(int maxAmount, int requestedAmount) {
        return maxAmount >= requestedAmount;
    }

    private int getActualLoanAmount(int maxAmount) {
        return Math.min(maxAmount, this.maxAmount);
    }

    private int calculateMaxAmount(int period, int creditModifier) {
        return creditModifier * period;
    }

    private LoanResponseDto buildLoanResponseDto(boolean decision, int maxAmount) {
        return LoanResponseDto.builder()
                .decision(decision)
                .maxAmount(maxAmount)
                .build();
    }

    private int computeRequiredPeriod(int loanAmount, int creditModifier) {
        if (creditModifier == 0) {
            throw new IllegalArgumentException("Credit modifier cannot be zero");
        }

        double requiredPeriod = Math.ceil((double) loanAmount / creditModifier);

        return (int) requiredPeriod;
    }
}