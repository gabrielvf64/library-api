package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateCustomerRequest(@NotBlank String name,
                                    @NotBlank @CPF(message = "CPF inválido") String cpf) {
}
