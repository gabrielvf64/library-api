package com.box.library.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryUserServiceTest {

    @Mock
    private LibraryUserRepository userRepository;

    @InjectMocks
    private LibraryUserService userService;

    @Test
    void testCreateLibraryUser() {
        LibraryUser user = new LibraryUser("Eduardo Rodrigues", "12345678901");
        LibraryUser savedUser = new LibraryUser("Eduardo Rodrigues", "12345678901");
        savedUser.setId(1L);

        when(userRepository.save(any(LibraryUser.class))).thenReturn(savedUser);

        LibraryUser result = userService.create(user);

        assertNotNull(result.getId());
        assertEquals("Eduardo Rodrigues", result.getName());
        assertEquals("12345678901", result.getCpf());
    }
}
