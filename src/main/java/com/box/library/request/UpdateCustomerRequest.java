package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UpdateCustomerRequest(@NotBlank String name,
                                    @NotBlank @CPF String cpf) {
}
