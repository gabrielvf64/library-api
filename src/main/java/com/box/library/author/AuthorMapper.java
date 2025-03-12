package com.box.library.author;

import com.box.library.request.CreateAuthorRequest;
import com.box.library.request.UpdateAuthorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(CreateAuthorRequest request);
    Author toAuthor(UpdateAuthorRequest request);
    CreateAuthorRequest toCreateAuthorRequest(Author author);
    UpdateAuthorRequest toUpdateAuthorRequest(Author author);
}
