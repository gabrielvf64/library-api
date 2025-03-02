package com.box.library.user;

import com.box.library.request.CreateUserRequest;
import com.box.library.request.UpdatePasswordRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class LibraryUserController {

    private final LibraryUserService service;

    public LibraryUserController(LibraryUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LibraryUser> createUser(@RequestBody CreateUserRequest request) {
        var savedUser = service.createUser(request);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LibraryUser>> findAll() {
        var libraryUsers = service.findAll();
        return libraryUsers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(libraryUsers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<LibraryUser> findById(@PathVariable Long id) {
        var libraryUser = service.findById(id);
        return ResponseEntity.ok(libraryUser);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<LibraryUser> updatePassword(@PathVariable Long id,
                                                      @Valid @RequestBody UpdatePasswordRequest request) {
        var updatedPassword = service.updatePassword(id, request);
        return new ResponseEntity<>(updatedPassword, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
