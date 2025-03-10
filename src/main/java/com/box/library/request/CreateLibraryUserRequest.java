package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

public record CreateLibraryUserRequest(@NotBlank String username) {
}
