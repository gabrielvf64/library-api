package com.box.library.loan;

import com.box.library.request.CreateLoan;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Loan>> findAll() {
        var loansList = service.findAll();
        return loansList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loansList);
    }

    // TODO[2]: Criação de empréstimo
    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody CreateLoan request) {
        var savedLoan = service.create(request);
        return ResponseEntity.ok(savedLoan);
    }

    // TODO[3]: Devolução de empréstimo
    @PostMapping("/{loanId}/return")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long loanId) {
        var loan = service.returnLoan(loanId);
        return ResponseEntity.ok(loan);
    }

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
