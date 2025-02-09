package com.box.library.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public LibraryUser create(LibraryUser user) {
        return repository.save(user);
    }

    public List<LibraryUser> findAll() {
        return repository.findAll();
    }
}
