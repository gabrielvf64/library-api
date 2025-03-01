package com.box.library.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findByUsername(String username);

//    @Query("SELECT u.role FROM LibraryUser u WHERE u.username = :username")
    Role findRoleByUsername(String username);
}
