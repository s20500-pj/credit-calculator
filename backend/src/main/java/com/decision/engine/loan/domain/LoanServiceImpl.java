package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import com.decision.engine.loan.dto.PersonDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final PersonDao personDao;

    @Override
    public LoanResponseDto getLoanDecision(LoanRequestDto loanRequestDto) {
        PersonDto personDto = personDao.findByIdentifier(loanRequestDto.getIdentifier());
        if (personDto.isDebt()) {
            return buildLoanResponseDto(false, 0);
        }
        int maxAmount = calculateMaxAmount(loanRequestDto.getPeriod(), personDto.getCreditModifier());
        boolean decision = maxAmount > loanRequestDto.getAmount();

        return buildLoanResponseDto(decision, Math.min(maxAmount, 10000));
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
}