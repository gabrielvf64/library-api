package com.box.library.user;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

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
}
