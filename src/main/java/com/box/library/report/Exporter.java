package com.box.library.report;

import com.box.library.loan.Loan;
import com.box.library.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Exporter {
    String export(List<Loan> loans, LoanStatus status);
}
