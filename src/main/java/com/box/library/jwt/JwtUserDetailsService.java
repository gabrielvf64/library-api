package com.box.library.jwt;

import com.box.library.response.JwtTokenResponse;
import com.box.library.user.LibraryUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final LibraryUserService libraryUserService;

    public JwtUserDetailsService(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUserDetails(libraryUserService.findByUsername(username));
    }

    public JwtTokenResponse getJwtToken(String username) {
        var libraryUserRole = libraryUserService.findRoleByUsername(username);
        return JwtUtils.createToken(username, libraryUserRole.name());
    }
}
