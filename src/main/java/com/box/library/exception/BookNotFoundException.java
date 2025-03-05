package com.box.library.exception;

import java.util.List;

// TODO[6]: Herança
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Book not found with id: " + bookId);
    }

    public BookNotFoundException(List<Long> booksIds) {
        super("The following books with id " + booksIds + " were not found.");
    }
}
