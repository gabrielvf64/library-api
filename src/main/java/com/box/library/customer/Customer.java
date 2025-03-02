package com.box.library.customer;

import com.box.library.request.CreateCustomerRequest;
import com.box.library.user.LibraryUser;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private LibraryUser user;

    // TODO: Por o endereço do cliente

    public Customer(CreateCustomerRequest request) {
        this.name = request.name();
        this.cpf = request.cpf();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LibraryUser getUser() {
        return user;
    }

    public void setUser(LibraryUser user) {
        this.user = user;
    }
}
