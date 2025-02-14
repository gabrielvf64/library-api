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

    public LibraryUser findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public LibraryUser updateUser(Long id, LibraryUser updatedUser) {
        LibraryUser existingUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if ((updatedUser.getName() == null || updatedUser.getName().isEmpty()) &&
                (updatedUser.getCpf() == null || updatedUser.getCpf().isEmpty())) {
            throw new IllegalArgumentException("O corpo da requisição está vazio ou incompleto.");
        }

        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getCpf() != null && !updatedUser.getCpf().isEmpty()) {
            existingUser.setCpf(updatedUser.getCpf());
        }

        return repository.save(existingUser);
    }
}
