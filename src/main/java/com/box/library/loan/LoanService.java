package com.box.library.loan;

import com.box.library.request.CreateLoan;
import org.springframework.stereotype.Service;

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

    public List<Loan> getLoansByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
