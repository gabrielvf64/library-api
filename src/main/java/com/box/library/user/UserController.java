package com.box.library.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LibraryUser> create(@RequestBody LibraryUser user) {
        return ResponseEntity.ok(service.create(user));
    }

    @GetMapping
    public ResponseEntity<List<LibraryUser>> findAll() {
        List<LibraryUser> users = service.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }
}
