package com.box.library.loan;

import com.box.library.exception.LoanNotFoundException;
import com.box.library.report.Exporter;
import com.box.library.request.CreateLoan;
import com.box.library.response.ReportResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository repository;
    private final List<Exporter> exporters;

    public LoanService(LoanRepository repository, List<Exporter> exporters) {
        this.repository = repository;
        this.exporters = exporters;
    }

    @Cacheable(value = "loans")
    public List<Loan> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "loansByUser", key = "#userId")
    public List<Loan> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @CacheEvict(value = "loans", allEntries = true)
    public Loan create(CreateLoan request) {
        var entity = new Loan(request.userId(), request.booksIds());
        return repository.save(entity);
    }

    @Cacheable(value = "loan", key = "#loanId")
    public Loan findById(Long loanId) {
        return repository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
    }

    @CachePut(value = "loan", key = "#loanId")
    @CacheEvict(value = "loans", allEntries = true)
    public Loan returnLoan(Long loanId) {
        var loan = findById(loanId);

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.FINISHED);

        return repository.save(loan);
    }

    @Cacheable(value = "loanReports", key = "#format + #status")
    public ReportResponse generateLoanReport(String format, LoanStatus status) {

        var filteredLoans = repository.findByStatus(status);

        var exporter = getExporterFromFileFormat(format);

        var reportContent = exporter.export(filteredLoans, status);

        var contentType = getContentType(format);

        var extension = getFileExtension(format);

        return new ReportResponse(reportContent, contentType, extension);
    }

    private Exporter getExporterFromFileFormat(String format) {
        return exporters.stream()
                .filter(e -> e.getFileExtension().equalsIgnoreCase(format))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato não suportado: " + format));
    }

    private static String getContentType(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "text/csv";
            case "html" -> "text/html";
            default -> throw new IllegalArgumentException("Formato não suportado: " + format);
        };
    }

    // TODO[13]: Switch expressions
    private static String getFileExtension(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "csv";
            case "html" -> "html";
            default -> throw new IllegalArgumentException("Formato não suportado: " + format);
        };
    }
}
