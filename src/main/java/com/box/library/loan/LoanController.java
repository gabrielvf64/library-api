package com.box.library.loan;

import com.box.library.request.CreateLoanRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public List<Loan> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Loan createLoan(@RequestBody CreateLoanRequest request) {
        return service.createLoan(request);
    }

    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUserId(@PathVariable Long userId) {
        return service.getLoansByUserId(userId);
    }

    @PutMapping("/return/{loanId}")
    public Loan returnLoan(@PathVariable Long loanId) {
        return service.returnLoan(loanId);
    }
}
