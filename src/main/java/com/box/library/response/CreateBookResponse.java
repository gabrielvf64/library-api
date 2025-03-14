package com.box.library.response;

import java.util.List;

public record CreateBookResponse(Long id, String title, List<String> authors,
                                 String publisher, String isbn, String status ) {
}
