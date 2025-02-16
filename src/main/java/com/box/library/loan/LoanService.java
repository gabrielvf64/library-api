package com.box.library.loan;

import com.box.library.report.Exporter;
import com.box.library.request.CreateLoan;
import com.box.library.response.ReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository repository;

    private final List<Exporter> exporters;

    public LoanService(LoanRepository repository,
                       List<Exporter> exporters) {
        this.repository = repository;
        this.exporters = exporters;
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public Loan create(CreateLoan request) {
        Loan entity = new Loan(request.userId(), request.booksIds());
        return repository.save(entity);
    }

    public ReportResponse generateLoanReport(String format, LoanStatus status) {
        List<Loan> filteredLoans = repository.findByStatus(status);

        Exporter exporter = getExporterFromFileFormat(format);

        String reportContent = exporter.export(filteredLoans, status);

        String contentType = getContentType(format);

        String extension = getFileExtension(format);

        return new ReportResponse(reportContent, contentType, extension);
    }

    private Exporter getExporterFromFileFormat(String format) {
        return exporters.stream()
                .filter(e -> e.getFileExtension().equalsIgnoreCase(format))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato não supoertado: " + format));
    }

    private static String getContentType(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "text/csv";
            case "html" -> "text/html";
            default -> throw new IllegalArgumentException("Formato não supoertado: " + format);
        };
    }

    private static String getFileExtension(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "csv";
            case "html" -> "html";
            default -> throw new IllegalArgumentException("Formato não supoertado: " + format);
        };
    }
}
