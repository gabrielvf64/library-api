package com.box.library.jwt;

import com.box.library.user.LibraryUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private LibraryUser libraryUser;

    public JwtUserDetails(LibraryUser libraryUser) {
        super(libraryUser.getUsername(), libraryUser.getPassword(),
                AuthorityUtils.createAuthorityList(libraryUser.getRole().name()));
    }

    public Long getUserId() {
        return this.libraryUser.getId();
    }

    public String getRole() {
        return this.libraryUser.getRole().name();
    }
}
