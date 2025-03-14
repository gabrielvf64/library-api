package com.box.library.user;

import com.box.library.exception.UserNotFoundException;
import com.box.library.request.UpdateLibraryUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserService {

    private final LibraryUserRepository repository;

    public LibraryUserService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public LibraryUser createUser(LibraryUser user) {
        return repository.save(user);
    }

    @Cacheable(value = "users")
    public List<LibraryUser> findAll() {
        return repository.findAll();
    }

    @CachePut(value = "user", key = "#id")
    @CacheEvict(value = "users", allEntries = true)
    public LibraryUser update(Long id, UpdateLibraryUser request) {
        var entity = findById(id);
        entity.setUsername(request.username());
        return repository.save(entity);
    }

    @Cacheable(value = "user", key = "#id")
    public LibraryUser findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @CacheEvict(value = {"users", "user"}, key = "#id", allEntries = true)
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
