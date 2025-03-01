package com.box.library.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank String username,
                                @NotBlank String password,
                                @NotBlank String role,
                                @NotBlank String cpf,
                                @NotBlank String name) {
}
