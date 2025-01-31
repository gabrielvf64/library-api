package com.box.library.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }
}
