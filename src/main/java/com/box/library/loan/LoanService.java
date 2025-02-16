package com.box.library.loan;

import com.box.library.exception.LoanNotFoundException;
import com.box.library.request.CreateLoan;
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

    public Loan create(CreateLoan request) {
        Loan entity = new Loan(request.userId(), request.booksIds());
        return repository.save(entity);
    }

    public Loan findById(Long loanId) {
        return repository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
    }

    public Loan returnLoan(Long loanId) {
        var loan = findById(loanId);

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.FINISHED);

        return repository.save(loan);
    }
}
