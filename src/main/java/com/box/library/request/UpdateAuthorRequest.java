package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateAuthorRequest(@NotBlank String name,
                                  @NotBlank List<Long> booksIds) {
}
