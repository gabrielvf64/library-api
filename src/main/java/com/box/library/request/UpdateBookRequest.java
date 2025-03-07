package com.box.library.request;

import com.box.library.book.BookStatus;

import java.util.List;

public record UpdateBookRequest(String title, List<Long> authorsIds, String publisher, String ISBN, BookStatus status) {
}
