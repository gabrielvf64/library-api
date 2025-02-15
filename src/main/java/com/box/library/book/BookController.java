package com.box.library.book;

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

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book savedBook = service.create(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = service.findAll();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable Long bookId) {
        return ResponseEntity.ok(service.findById(bookId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long bookId) {
        service.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> update(@PathVariable Long bookId, @RequestBody UpdateBook request) {
        var updatedEntity = service.update(bookId, request);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }
}
