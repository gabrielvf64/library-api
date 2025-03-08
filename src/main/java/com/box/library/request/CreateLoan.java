package com.box.library.request;

import java.util.List;

public record CreateLoan(Long customerId, List<Long> booksIds) {
}
