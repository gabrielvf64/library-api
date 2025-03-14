package com.box.library.customer;

import com.box.library.exception.UserNotFoundException;
import com.box.library.jwt.JwtUserDetails;
import com.box.library.request.CreateCustomerRequest;
import com.box.library.request.UpdateCustomerRequest;
import com.box.library.user.LibraryUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Customer entity = toEntity(request);

        entity.setUser(libraryUserService.findById(jwtUserDetails.getId()));

        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return repository.findAll();
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

    public Customer update(Long id, UpdateCustomerRequest request) {
        var entity = findById(id);

        entity.setName(request.name());
        entity.setCpf(request.cpf());

        return repository.save(entity);
    }

    public void deleteById(Long id) {
        if (doesNotExitsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private boolean doesNotExitsById(Long id) {
        return !repository.existsById(id);
    }

    private Customer toEntity(CreateCustomerRequest request) {
        return new Customer(request.name(), request.cpf(), request.address());
    }
}
