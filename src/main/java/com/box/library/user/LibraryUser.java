package com.box.library.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String name;

    public LibraryUser() {
    }

    public LibraryUser(Long id, String cpf, String name) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

