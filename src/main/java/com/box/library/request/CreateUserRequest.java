package com.box.library.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank String username,
                                @NotBlank String password,
                                @NotBlank String role) {
}
