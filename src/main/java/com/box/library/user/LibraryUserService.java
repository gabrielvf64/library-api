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

    public LibraryUser createUser(LibraryUser user) {
        return repository.save(user);
    }

    public List<LibraryUser> findAllUsers() {
        return repository.findAll();
    }

    public LibraryUser findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
