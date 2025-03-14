package com.box.library.request;

import com.box.library.address.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CreateCustomerRequest(@NotBlank String name,
                                    @NotBlank @CPF String cpf,
                                    @NotNull Address address) {
}
