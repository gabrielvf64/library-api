package com.box.library.customer;

import com.box.library.jwt.JwtUserDetails;
import com.box.library.request.CreateCustomerRequest;
import com.box.library.user.LibraryUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final LibraryUserService libraryUserService;

    public CustomerService(CustomerRepository repository, LibraryUserService libraryUserService) {
        this.repository = repository;
        this.libraryUserService = libraryUserService;
    }

    @Transactional
    public Customer save(CreateCustomerRequest request, JwtUserDetails jwtUserDetails) {
        Customer entity = new Customer(request);

        entity.setUser(libraryUserService.findById(jwtUserDetails.getId()));

        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer id [" + id + "] not found"));
    }

    @Transactional(readOnly = true)
    public Customer findUserById(Long id) {
        return repository.findByUserId(id);
    }
}
