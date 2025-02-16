package com.box.library.loan;

import com.box.library.request.CreateLoan;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody CreateLoan request) {
        Loan savedLoan = service.create(request);
        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> findAll() {
        List<Loan> loansList = service.findAll();
        return loansList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loansList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable Long userId) {
        List<Loan> loansList = service.getLoansByUser(userId);
        return loansList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loansList);
    }
}
