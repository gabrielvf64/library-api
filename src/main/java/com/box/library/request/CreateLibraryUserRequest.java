package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateLibraryUserRequest(@NotBlank String username,
                                       @NotBlank String password,
                                       @NotBlank String role,
                                       @NotBlank @CPF String cpf,
                                       @NotBlank String name) {
}
