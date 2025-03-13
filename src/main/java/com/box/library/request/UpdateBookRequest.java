package com.box.library.request;

import com.box.library.book.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateBookRequest(@NotBlank String title,
                                @NotEmpty List<Long> authorsIds,
                                @NotBlank String publisher,
                                @NotBlank String isbn,
                                @NotNull BookStatus status) {
}
