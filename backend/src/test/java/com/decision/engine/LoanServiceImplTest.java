package com.decision.engine;

import com.decision.engine.loan.domain.LoanServiceImpl;
import com.decision.engine.loan.domain.PersonDao;
import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import com.decision.engine.loan.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private PersonDao personDao;

    @Test
    public void testPositiveGetLoanDecision() {
        PersonDto mockPerson = new PersonDto(false, 100);
        when(personDao.findByIdentifier(anyString())).thenReturn(mockPerson);

        LoanRequestDto request = new LoanRequestDto(anyString(), 15, 1000);
        LoanResponseDto response = loanService.getLoanDecision(request);

        assertTrue(response.isDecision());
        assertEquals(response.getMaxAmount(), 1500);
    }

    @Test
    public void testNegativeGetLoanDecision() {
        PersonDto mockPerson = new PersonDto(false, 100);
        when(personDao.findByIdentifier(anyString())).thenReturn(mockPerson);

        LoanRequestDto request = new LoanRequestDto(anyString(), 50, 20000);
        LoanResponseDto response = loanService.getLoanDecision(request);

        assertFalse(response.isDecision());
        assertEquals(response.getMaxAmount(), 5000);
    }

    @Test
    public void testDebtGetLoanDecision() {
        PersonDto mockPerson = new PersonDto(true, null);
        when(personDao.findByIdentifier(anyString())).thenReturn(mockPerson);

        LoanRequestDto request = new LoanRequestDto(anyString(), 60, 10000);
        LoanResponseDto response = loanService.getLoanDecision(request);

        assertFalse(response.isDecision());
        assertEquals(response.getMaxAmount(), 0);
    }
}