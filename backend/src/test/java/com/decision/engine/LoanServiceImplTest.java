package com.decision.engine;

import com.decision.engine.loan.domain.LoanServiceImpl;
import com.decision.engine.loan.domain.PersonDao;
import com.decision.engine.loan.dto.LoanRequestDto;
import com.decision.engine.loan.dto.LoanResponseDto;
import com.decision.engine.loan.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
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

    @Mock
    private PersonDao personDao;

    private LoanServiceImpl loanService;

    @BeforeEach
    public void setup() {
        int maxAmount = 10000;
        int minPeriod = 12;
        int maxPeriod = 60;
        loanService = new LoanServiceImpl(personDao, maxAmount, minPeriod, maxPeriod);
    }

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
        PersonDto mockPerson = new PersonDto(false, 200);
        when(personDao.findByIdentifier(anyString())).thenReturn(mockPerson);

        LoanRequestDto request = new LoanRequestDto(anyString(), 12, 3000);
        LoanResponseDto response = loanService.getLoanDecision(request);

        assertFalse(response.isDecision());
        assertEquals(response.getMaxAmount(), 2400);
        assertEquals(response.getRequiredPeriod(), 15);
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