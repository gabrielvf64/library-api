package com.box.library.loan;

import com.box.library.report.Exporter;
import com.box.library.request.CreateLoan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository repository;

    private final Exporter exporter;

    public LoanService(LoanRepository repository, Exporter exporter) {
        this.repository = repository;
        this.exporter = exporter;
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public Loan create(CreateLoan request) {
        Loan entity = new Loan(request.userId(), request.booksIds());
        return repository.save(entity);
    }

    public String generateLoanReport(LoanStatus status) {
        List<Loan> filteredLoans = repository.findByStatus(status);
        return exporter.export(filteredLoans, status);
    }
}
