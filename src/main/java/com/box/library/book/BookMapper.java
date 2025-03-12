package com.box.library.book;

import com.box.library.request.CreateBookRequest;
import com.box.library.request.UpdateBookRequest;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface BookMapper {



    Book toBook(CreateBookRequest request);
    Book toBook(UpdateBookRequest request);

    CreateBookRequest toCreateBookRequest(Book book);
    UpdateBookRequest toUpdateBookRequest(Book book);


}
