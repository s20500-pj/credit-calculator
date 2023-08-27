package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;

public interface LoanService {

    LoanResponseDto getLoanDecision(LoanRequestDto loanRequestDto);
}