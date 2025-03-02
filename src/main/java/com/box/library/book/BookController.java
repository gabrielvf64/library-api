package com.box.library.book;

import com.box.library.request.CreateBook;
import com.box.library.request.UpdateBook;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros")
public class BookController {

    // TODO[5]: CRUD de livros

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // TODO[1]: Criação de livro
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody CreateBook request) {

        // TODO[9]: Usando var
        var savedBook = service.create(request);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        var books = service.findAll();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        var book = service.findById(id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody UpdateBook request) {
        var updatedEntity = service.update(id, request);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    // TODO[4]: Busca de livros por filtros
    @GetMapping("/filter")
    public ResponseEntity<List<Book>> findAllByFilter(@RequestParam(required = false) String author, @RequestParam(required = false) String title,
                                                      @RequestParam(required = false) String isbn, @RequestParam(required = false) String publisher) {
        var books = service.findAllByFilter(author, title, isbn, publisher);
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }
}
