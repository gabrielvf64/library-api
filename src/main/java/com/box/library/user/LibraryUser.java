package com.box.library.user;

import com.box.library.commom.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;
    private String name;

    @Embedded
    private Address address;

    public LibraryUser() {
    }

    public LibraryUser(Long id, String cpf, String name) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
    }
}

