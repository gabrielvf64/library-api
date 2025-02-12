package com.box.library.user;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import com.box.library.exception.UserNotFoundException;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class LibraryUserController {

    private final LibraryUserService service;

    public LibraryUserController(LibraryUserService service) {
        this.service = service;
    }

    // Endpoint to create a user
    @PostMapping
    public ResponseEntity<LibraryUser> createUser(@RequestBody LibraryUser user) {
        LibraryUser savedUser = service.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Endpoint to list all users
    @GetMapping
    public ResponseEntity<List<LibraryUser>> findAllUsers() {
        List<LibraryUser> users = service.findAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    // Endpoint to search for user by ID
    @GetMapping("/{id}")
    public ResponseEntity<LibraryUser> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    // Endpoint to update a user (old)
//    @PutMapping("/{id}")
//    public ResponseEntity<LibraryUser> updateUser(@PathVariable Long id, @RequestBody LibraryUser updatedUser) {
//        LibraryUser user = service.updateUser(id, updatedUser);
//        return ResponseEntity.ok(user);
//    }

    // Endpoint to update a user (new)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody LibraryUser updatedUser) {
        try {
            LibraryUser user = service.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build(); // Returns 404 if the user is not found
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar usuário");
        }
    }

    // Endpoint to delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content, se o usuário for removido com sucesso
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build(); // Retorna 404, se o usuário não for encontrado
        }
    }

}
