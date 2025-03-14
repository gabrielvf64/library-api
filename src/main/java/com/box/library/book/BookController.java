package com.box.library.book;

import com.box.library.request.CreateBookRequest;
import com.box.library.request.UpdateBookRequest;
import com.box.library.response.BookResponse;
import com.box.library.response.GenericPagedResponse;
import com.box.library.response.CreateBookResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CreateBookResponse> create(@Valid @RequestBody CreateBookRequest request) {
        var savedBook = service.create(request);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        var books = service.findAll();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPagedResponse<BookResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPageable(pageable));
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
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest request) {
        var updatedEntity = service.update(id, request);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookResponse>> findAllByFilter(@RequestParam(required = false) String author,
                                                              @RequestParam(required = false) String title,
                                                              @RequestParam(required = false) String isbn,
                                                              @RequestParam(required = false) String publisher) {
        var books = service.findAllByFilter(author, title, isbn, publisher);
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }
}
