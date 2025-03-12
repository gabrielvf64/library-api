package com.box.library.user;

import com.box.library.request.UpdateLibraryUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibraryUserMapper {

    LibraryUser toLibraryUser(UpdateLibraryUser request);
    UpdateLibraryUser toUpdateLibraryUser(LibraryUser libraryUser);
}
