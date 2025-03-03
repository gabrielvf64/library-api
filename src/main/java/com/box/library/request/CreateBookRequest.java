package com.box.library.request;

import java.util.List;

public record CreateBookRequest(String title, List<Long> authorsIds, String publisher, String ISBN) {
}
