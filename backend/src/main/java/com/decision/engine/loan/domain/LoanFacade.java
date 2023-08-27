package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import com.decision.engine.loan.validation.LoanRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoanFacade {

    private final LoanService loanService;
    private final LoanRequestValidator loanRequestValidator;

    public LoanResponseDto getLoanDecision(LoanRequestDto loanRequestDto) {
        loanRequestValidator.throwIfNotValid(loanRequestDto);
        return loanService.getLoanDecision(loanRequestDto);
    }
}