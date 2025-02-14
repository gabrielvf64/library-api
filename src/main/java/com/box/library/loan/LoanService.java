package com.box.library.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanRepository repository;

    public LoanService(LoanRepository repository) {
        this.repository = repository;
    }
}
