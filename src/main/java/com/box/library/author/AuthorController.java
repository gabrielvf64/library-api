package com.box.library.author;

import com.box.library.request.CreateAuthor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@Tag(name = "Autores", description = "Endpoints para gerenciamento de autores")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Author>> findAll() {
        var authorsList = service.findAll();
        return authorsList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(authorsList);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody CreateAuthor request) {
        var savedAuthor = service.create(request);
        return ResponseEntity.ok(savedAuthor);
    }
}
