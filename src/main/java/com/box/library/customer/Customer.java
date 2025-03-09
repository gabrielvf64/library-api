package com.box.library.customer;

import com.box.library.request.CreateCustomerRequest;
import com.box.library.user.LibraryUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LibraryUser user;

    // TODO: Por o endereço do cliente

    public Customer(CreateCustomerRequest request) {
        this.name = request.name();
        this.cpf = request.cpf();
    }
}
