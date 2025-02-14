package com.box.library.user;

import com.box.library.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity <List<LibraryUser>> findAll(){
        List <LibraryUser> libraryUsers  = service.findAllUsers();
        return libraryUsers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(libraryUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUser> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(@org.jetbrains.annotations.NotNull UserNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
