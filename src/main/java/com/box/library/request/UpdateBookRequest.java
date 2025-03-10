package com.box.library.request;

import com.box.library.book.BookStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateBookRequest(@NotBlank String title,
                                @NotBlank List<Long> authorsIds,
                                @NotBlank String publisher,
                                @NotBlank String isbn,
                                @NotBlank BookStatus status) {
}
