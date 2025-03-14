package com.box.library.response;

import java.time.LocalDate;
import java.util.List;

public record FindAllLoansResponse(Long id, String customerName, List<BookDetailsResponse> books, LocalDate loanDate,
                                   LocalDate expectedReturnDate, LocalDate returnDate, String status) {
}
