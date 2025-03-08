package com.box.library.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {
        super("Author not found with id: " + id);
    }
}
