package com.box.library.loan;

import com.box.library.exception.LoanNotFoundException;
import com.box.library.request.CreateLoanRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository repository;

    public LoanService(LoanRepository repository) {
        this.repository = repository;
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public Loan createLoan(CreateLoanRequest request) {
        Loan loan = new Loan(request.userId(), request.bookIds());
        return repository.save(loan);
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return repository.findByUserId(userId);
    }


    public Loan returnLoan(Long loanId) {
        return repository.findById(loanId)
                .map(loan -> {
                    loan.setReturnDate(LocalDate.now());
                    loan.setStatus(LoanStatus.FINISHED);
                    return repository.save(loan);
                })
                .orElseThrow(() -> new LoanNotFoundException(loanId));
    }
}
