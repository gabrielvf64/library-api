package com.box.library.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String cpf;
    private String name;

    public LibraryUser(String username, String password, String role, String cpf, String name) {
        this.username = username;
        this.password = password;
        this.role = Role.valueOf(role);
        this.cpf = cpf;
        this.name = name;
    }
}

