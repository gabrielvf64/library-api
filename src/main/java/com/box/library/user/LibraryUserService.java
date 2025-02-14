package com.box.library.user;

import org.springframework.stereotype.Service;
import com.box.library.exception.UserNotFoundException;

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

    public LibraryUser findUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public LibraryUser updateUser(Long userId, LibraryUser updateUser) {
        LibraryUser existingUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (updateUser.getName() != null && !updateUser.getName().isEmpty()) {
            existingUser.setName(updateUser.getName());
        }

        if (updateUser.getCpf() != null && !updateUser.getCpf().isEmpty()) {
            existingUser.setCpf(updateUser.getCpf());
        }

        return repository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        repository.deleteById(userId);
    }

    public List<LibraryUser> findAllUsers() {
        return repository.findAll();
    }

    public LibraryUser findById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
