package com.box.library.user;

import com.box.library.exception.UserNotFoundException;
import com.box.library.request.UpdateLibraryUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public LibraryUser findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public LibraryUser findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public LibraryUser update(Long id, UpdateLibraryUser request) {
        var entity = findById(id);

        entity.setName(request.name());
        entity.setCpf(request.cpf());

        return repository.save(entity);
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
