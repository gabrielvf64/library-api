package com.box.library.report;

import com.box.library.loan.Loan;
import com.box.library.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public sealed interface Exporter permits HtmlExporter, CsvExporter {

    String export(List<Loan> loans, LoanStatus status);

    String getFileExtension();
}
