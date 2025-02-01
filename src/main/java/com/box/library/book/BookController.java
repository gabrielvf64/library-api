package com.box.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllPageable(@RequestHeader(value = "Range", required = false) String range) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> booksPage = service.findAll(pageable);
        return ResponseEntity.ok()
                .headers(createResponseHeaders(booksPage))
                .body(booksPage.getContent());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book savedBook = service.create(book);
        return ResponseEntity.ok(savedBook);
    }

    private HttpHeaders createResponseHeaders(Page<Book> booksPage) {
        long totalBooks = service.count();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "books " + 0 + "-"
                + booksPage.getNumberOfElements()
                + "/" + totalBooks);
        return headers;
    }
}
