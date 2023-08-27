package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/loan")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class LoanCalculator {

    private final LoanFacade loanFacade;

    @PostMapping("/decision")
    ResponseEntity<LoanResponseDto> getLoanDecision(@RequestBody @Validated LoanRequestDto loanRequestDto) {
        return ResponseEntity.ok(loanFacade.getLoanDecision(loanRequestDto));
    }
}