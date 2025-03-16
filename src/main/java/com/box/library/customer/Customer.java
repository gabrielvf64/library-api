package com.box.library.customer;

import com.box.library.address.Address;
import com.box.library.loan.Loan;
import com.box.library.user.LibraryUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String name;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LibraryUser user;

    @OneToMany
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("customer")
    private List<Loan> loans;

    public Customer(String name, String cpf, Address address) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
    }
}
