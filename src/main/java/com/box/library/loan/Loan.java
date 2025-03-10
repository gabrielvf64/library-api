package com.box.library.loan;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private List<Long> booksIds;

    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private LoanStatus status;

    @Builder
    public Loan(Long userId, List<Long> booksIds) {
        this.userId = userId;
        this.booksIds = booksIds;
        this.loanDate = LocalDate.now();
        this.expectedReturnDate = this.loanDate.plusDays(3);
        this.status = LoanStatus.ACTIVE;
    }

}
