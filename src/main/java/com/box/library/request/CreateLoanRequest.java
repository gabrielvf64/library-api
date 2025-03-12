package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateLoanRequest(@NotBlank Long customerId,
                                @NotEmpty List<Long> booksIds) {
}
