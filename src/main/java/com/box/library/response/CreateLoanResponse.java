package com.box.library.response;

import java.time.LocalDate;
import java.util.List;

public record CreateLoanResponse(Long id, String customerName, List<CreateLoanBookDetailsResponse> books,
                                 LocalDate loanDate, LocalDate expectedReturnDate, String status) {
}
