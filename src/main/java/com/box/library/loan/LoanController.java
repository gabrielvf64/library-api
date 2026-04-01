package com.box.library.loan;

import com.box.library.request.CreateLoanRequest;
import com.box.library.response.CreateLoanResponse;
import com.box.library.response.FindAllLoansResponse;
import com.box.library.response.FindLoanByCustomerResponse;
import com.box.library.response.ReturnLoanResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<FindAllLoansResponse>> findAll() {
        var loansList = service.findAll();
        return loansList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loansList);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<FindLoanByCustomerResponse> findByCustomerId(@PathVariable Long id) {
        var loans = service.findByCustomerId(id);
        return ResponseEntity.ok(loans);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<CreateLoanResponse> create(@Valid @RequestBody CreateLoanRequest request) {
        var savedLoan = service.create(request);
        return ResponseEntity.ok(savedLoan);
    }

    @PostMapping("/{loanId}/return")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<ReturnLoanResponse> returnLoan(@PathVariable Long loanId) {
        var loan = service.returnLoan(loanId);
        return ResponseEntity.ok(loan);
    }

    @GetMapping(value = "/report", produces = {"text/html", "text/csv"})
    @PreAuthorize("hasAuthority('ADMIN')")
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
