package com.box.library.loan;

import com.box.library.request.CreateLoan;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
@Tag(name = "Empréstimos", description = "Endpoints para gerenciamento de empréstimos")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Loan>> findAll() {
        var loansList = service.findAll();
        return loansList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loansList);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<List<Loan>> findByUserId(@PathVariable Long userId) {
        var loans = service.findByUserId(userId);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<Loan> create(@RequestBody CreateLoan request) {
        var savedLoan = service.create(request);
        return ResponseEntity.ok(savedLoan);
    }

    @PostMapping("/{loanId}/return")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long loanId) {
        var loan = service.returnLoan(loanId);
        return ResponseEntity.ok(loan);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/report", produces = {"text/html", "text/csv"})
    public ResponseEntity<String> exportLoans(
            @RequestParam(defaultValue = "html") String format,
            @RequestParam LoanStatus status) {

        var reportResponse = service.generateLoanReport(format, status);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio." + reportResponse.getExtension())
                .contentType(MediaType.parseMediaType(reportResponse.getContentType()))
                .body(reportResponse.getContent());
    }
}
