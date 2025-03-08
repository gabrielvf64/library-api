package com.box.library.request;

import java.util.List;

public record CreateAuthorRequest(String name, List<Long> booksIds) {
}
