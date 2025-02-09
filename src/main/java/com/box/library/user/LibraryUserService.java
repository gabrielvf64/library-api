package com.box.library.user;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryUserService {

    private final LibraryUserRepository repository;

    public LibraryUserService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    public LibraryUser create(LibraryUser user) {
        return repository.save(user);
    }

    public List<LibraryUser> findAll() {
        return repository.findAll();
    }

    public Optional<LibraryUser> findById(Long id) {
        return repository.findById(id);
    }
}
