package com.box.library.loan;

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
        Loan loan = repository.findById(loanId).orElseThrow();
        loan.setStatus(LoanStatus.FINISHED);
        loan.setReturnDate(LocalDate.now());
        return repository.save(loan);
    }


}
