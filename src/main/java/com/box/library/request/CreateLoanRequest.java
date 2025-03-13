package com.box.library.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateLoanRequest(@NotNull Long customerId,
                                @NotEmpty List<Long> booksIds) {
}
