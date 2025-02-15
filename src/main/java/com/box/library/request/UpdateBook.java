package com.box.library.request;

import com.box.library.book.BookStatus;

public record UpdateBook(String title, String author, String publisher, String ISBN, BookStatus status) {
}
