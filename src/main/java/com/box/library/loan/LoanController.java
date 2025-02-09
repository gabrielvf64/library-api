package com.box.library.loan;

import com.box.library.request.CreateLoanRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
@Tag(name = "Empréstimos", description = "Endpoints para gerenciamento empréstimos de livros")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> findAll() {
        List<Loan> loans = service.findAll();
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest request) {
        Loan loan = service.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        List<Loan> loans = service.getLoansByUserId(userId);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

    @PutMapping("/return/{loanId}")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(service.returnLoan(loanId));
    }
}
