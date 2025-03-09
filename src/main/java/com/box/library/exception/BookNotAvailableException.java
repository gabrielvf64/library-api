package com.box.library.exception;

import java.util.List;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(List<Long> booksIds) {
        super("The following books with ids " + booksIds + " are not available.");
    }

}
