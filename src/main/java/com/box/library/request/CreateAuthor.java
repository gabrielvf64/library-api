package com.box.library.request;

import java.util.List;

public record CreateAuthor(String name, List<Long> booksId) {
}
