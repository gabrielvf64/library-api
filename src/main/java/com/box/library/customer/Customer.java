package com.box.library.customer;

import com.box.library.loan.Loan;
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

    @OneToMany
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("customer")
    private List<Loan> loans;

    public Customer(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
