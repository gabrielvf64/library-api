package com.box.library.user;

import com.box.library.request.UpdateLibraryUser;
import com.box.library.request.UpdatePasswordRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LibraryUser> createUser(@RequestBody LibraryUser user) {
        var savedUser = service.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<LibraryUser>> findAll() {
        var libraryUsers = service.findAll();
        return libraryUsers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(libraryUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUser> findById(@PathVariable Long id) {
        var libraryUser = service.findById(id);
        return ResponseEntity.ok(libraryUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryUser> update(@PathVariable Long id, @RequestBody UpdateLibraryUser request) {
        var updatedEntity = service.update(id, request);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @Valid @RequestBody UpdatePasswordRequest request) {
        service.updatePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
