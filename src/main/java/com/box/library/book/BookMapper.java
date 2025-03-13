package com.box.library.book;

import com.box.library.author.Author;
import com.box.library.request.CreateBookRequest;
import com.box.library.request.UpdateBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target="authors", source="authors")
    Book toBook(CreateBookRequest request, List<Author> authors);

    @Mapping(target="status", source="request.status", qualifiedByName = "stringToStatus")
    Book toBook(UpdateBookRequest request, Long id, List<Author> authors);

    @Named("stringToStatus")
    default BookStatus stringToStatus(String status) {
        return BookStatus.valueOf(status);
    }
}
