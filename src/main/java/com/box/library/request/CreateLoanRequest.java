package com.box.library.request;

import java.util.List;

public record CreateLoanRequest(Long customerId, List<Long> booksIds) {
}
