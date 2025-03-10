package com.box.library.user;

import com.box.library.customer.Address;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Embedded
    private Address address;

    public LibraryUser() {} // Empty constructor (required for Hibernate)

    public LibraryUser(String username, Address address) {
        this.username = username;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}

