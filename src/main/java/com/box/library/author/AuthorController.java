package com.box.library.author;

import com.box.library.request.CreateAuthorRequest;
import com.box.library.request.UpdateAuthorRequest;
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
    public ResponseEntity<Author> create(@RequestBody CreateAuthorRequest request) {
        var savedAuthor = service.create(request);
        return ResponseEntity.ok(savedAuthor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        var author = service.findById(id);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody UpdateAuthorRequest request) {
        var updatedAuthor = service.update(id, request);
        return ResponseEntity.ok(updatedAuthor);
    }

}
