package com.box.library.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByCustomerId(Long id);

    boolean existsByCustomerIdAndStatusIn(Long customerId, List<LoanStatus> statuses);
}
