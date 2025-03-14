package com.box.library.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record GenericPagedResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {
    public static <T> GenericPagedResponse<T> fromPage(Page<T> page) {
        return new GenericPagedResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}
