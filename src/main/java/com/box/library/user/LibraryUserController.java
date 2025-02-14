package com.box.library.user;

import com.box.library.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        LibraryUser savedUser = service.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<LibraryUser>> findAllUsers() {
        List<LibraryUser> users = service.findAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LibraryUser> findById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(service.findById(userId));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(404).body(new LibraryUser(userId, "Usuário com ID " + userId + " não encontrado.", ""));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<LibraryUser> updateUser(@PathVariable Long userId, @RequestBody LibraryUser user) {
        try {
            LibraryUser updatedUser = service.updateUser(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(404).body(new LibraryUser(userId, "Usuário com ID " + userId + " não encontrado.", ""));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            service.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"Usuário com ID " + userId + " não encontrado.\"}");
        }
    }
}
