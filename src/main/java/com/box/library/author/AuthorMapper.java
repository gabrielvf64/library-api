package com.box.library.author;

import com.box.library.book.Book;
import com.box.library.request.CreateAuthorRequest;
import com.box.library.request.UpdateAuthorRequest;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(CreateAuthorRequest request, List<Book> books);

    Author toAuthor(UpdateAuthorRequest request, List<Book> books, Long id);
}
