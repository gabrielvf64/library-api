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

    public List<LibraryUser> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        if (doesNotExitsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private boolean doesNotExitsById(Long id) {
        return !repository.existsById(id);
    }
}
