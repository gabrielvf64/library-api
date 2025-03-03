package com.box.library.request;

import java.util.List;

public record UpdateAuthorRequest(String name, List<Long> booksIds) {
}
