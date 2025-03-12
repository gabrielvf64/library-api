package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateBookRequest(@NotBlank String title,
                                @NotEmpty List<Long> authorsIds,
                                @NotBlank String publisher,
                                @NotBlank String isbn) {
}
