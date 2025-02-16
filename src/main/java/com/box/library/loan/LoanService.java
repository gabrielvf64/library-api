package com.box.library.loan;

import com.box.library.report.Exporter;
import com.box.library.request.CreateLoan;
import com.box.library.response.ReportResponse;
import com.box.library.user.LibraryUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository repository;
    private final LibraryUserRepository userRepository;
    private final List<Exporter> exporters;

    public LoanService(LoanRepository repository, LibraryUserRepository userRepository, List<Exporter> exporters) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.exporters = exporters;
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public List<Loan> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuário com ID " + userId + " não encontrado.");
        }
        return repository.findByUserId(userId);
    }

    public Loan create(CreateLoan request) {
        var entity = new Loan(request.userId(), request.booksIds());
        return repository.save(entity);
    }

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

    private static String getFileExtension(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "csv";
            case "html" -> "html";
            default -> throw new IllegalArgumentException("Formato não suportado: " + format);
        };
    }
}
