package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateBookRequest(@NotBlank String title,
                                @NotBlank List<Long> authorsIds,
                                @NotBlank String publisher,
                                @NotBlank String isbn) {
}
