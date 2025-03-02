package com.box.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(@NotBlank String name,
                                    @NotBlank @Size(min = 11, max = 11) String cpf) {
}
