package com.box.library.request;

import com.box.library.book.BookStatus;

import java.util.List;

public record UpdateBook(String title, List<Long> authorsId, String publisher, String ISBN, BookStatus status) {
}
