package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateAuthorRequest(@NotBlank String name,
                                  @NotEmpty List<Long> booksIds) {
}
