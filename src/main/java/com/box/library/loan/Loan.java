package com.box.library.loan;

import com.box.library.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<Long> booksIds;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("loans")
    private Customer customer;

    public Loan(Customer customer, List<Long> booksIds) {
        this.customer = customer;
        this.booksIds = booksIds;
        this.loanDate = LocalDate.now();
        this.expectedReturnDate = loanDate.plusDays(3);
        this.status = LoanStatus.ACTIVE;
    }

}
