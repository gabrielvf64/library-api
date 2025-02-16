package com.box.library.request;

import java.util.List;

public record CreateLoan(Long userId, List<Long> booksIds) {
}
