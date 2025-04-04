package com.box.library.user;

import com.box.library.exception.UserNotFoundException;
import com.box.library.request.CreateLibraryUserRequest;
import com.box.library.request.UpdateLibraryUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserService {

    private final LibraryUserRepository repository;

    public LibraryUserService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    public LibraryUser createUser(CreateLibraryUserRequest request) {
        return repository.save(toEntity(request));
    }

    public List<LibraryUser> findAll() {
        return repository.findAll();
    }

    public LibraryUser update(Long id, UpdateLibraryUser request) {
        var entity = findById(id);
        entity.setUsername(request.username());
        return repository.save(entity);
    }

    public LibraryUser findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteById(Long id) {
        if (doesNotExitsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private LibraryUser toEntity(CreateLibraryUserRequest request) {
        return new LibraryUser(request.username());
    }

    private boolean doesNotExitsById(Long id) {
        return !repository.existsById(id);
    }
}
