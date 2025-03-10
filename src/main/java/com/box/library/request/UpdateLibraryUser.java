package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateLibraryUser(@NotBlank String username) {
}
