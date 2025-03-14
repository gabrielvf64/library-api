package com.box.library.response;

import com.box.library.loan.Loan;

import java.util.List;

public record FindLoanByCustomerResponse(String customer, List<LoanDetailsResponse> loans) {
}
