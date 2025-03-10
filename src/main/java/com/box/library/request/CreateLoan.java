package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateLoan(@NotBlank Long userId,
                         @NotBlank List<Long> booksIds) {
}
