package com.box.library.user;

import com.box.library.exception.InvalidPasswordException;
import com.box.library.exception.UserNotFoundException;
import com.box.library.request.CreateLibraryUserRequest;
import com.box.library.request.UpdateLibraryUser;
import com.box.library.request.UpdatePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibraryUserService {

    private final LibraryUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public LibraryUserService(LibraryUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public LibraryUser createUser(CreateUserRequest request) {
        var entity = toEntity(request);

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        return repository.save(entity);
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

    @Transactional(readOnly = true)
    public Role findRoleByUsername(String username) {
        return repository.findRoleByUsername(username);
    }

    public LibraryUser update(Long id, UpdateLibraryUser request) {
        var entity = findById(id);
        entity.setUsername(request.username());
        return repository.save(entity);
    }

    public LibraryUser updatePassword(Long id, UpdatePasswordRequest request) {
        if (newPasswordIsNotEqualsToConfirmPassword(request)) {
            throw new InvalidPasswordException("Nova senha não é igual à confirmação de senha");
        }

        var libraryUser = findById(id);

        if (currentPasswordDoesNotMatch(request, libraryUser)) {
            throw new InvalidPasswordException("Senha atual não é igual à senha informada");
        }

        libraryUser.setPassword(passwordEncoder.encode(request.newPassword()));
        return libraryUser;
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

    private static boolean newPasswordIsNotEqualsToConfirmPassword(UpdatePasswordRequest request) {
        return !request.newPassword().equals(request.confirmPassword());
    }

    private boolean currentPasswordDoesNotMatch(UpdatePasswordRequest request, LibraryUser libraryUser) {
        return !passwordEncoder.matches(request.currentPassword(), libraryUser.getPassword());
    }

    private LibraryUser toEntity(CreateUserRequest request) {
        return new LibraryUser(request.username(), request.password(), request.role(),
                request.cpf(), request.name());
    }
}
