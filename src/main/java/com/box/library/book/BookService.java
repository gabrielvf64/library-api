package com.box.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Book create(Book book) {
        return repository.save(book);
    }
}
