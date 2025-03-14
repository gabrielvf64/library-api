package com.box.library.response;

import java.util.List;

public record BookResponse(Long bookId,
                           String title,
                           List<AuthorResponse> authors,
                           String publisher,
                           String isbn,
                           String status) {
}
