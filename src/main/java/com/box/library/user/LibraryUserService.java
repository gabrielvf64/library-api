package com.box.library.user;

import com.box.library.exception.UserNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserService {

    private final LibraryUserRepository repository;

    public LibraryUserService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    // Method to create a user
    public LibraryUser createUser(LibraryUser user) {
        return repository.save(user);
    }

    // Method to list all users
    public List<LibraryUser> findAllUsers() {
        return repository.findAll();
    }

    // Method to search for a user by ID
    public LibraryUser findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
