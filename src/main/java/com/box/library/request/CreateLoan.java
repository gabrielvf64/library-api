package com.box.library.request;

import java.util.List;

// TODO[10]: Usando record
public record CreateLoan(Long userId, List<Long> booksIds) {
}
