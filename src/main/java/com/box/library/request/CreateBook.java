package com.box.library.request;

import java.util.List;

public record CreateBook(String title, List<Long> authorsId, String publisher, String ISBN) {
}
