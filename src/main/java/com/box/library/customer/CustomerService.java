package com.box.library.customer;

import com.box.library.exception.UserNotFoundException;
import com.box.library.request.CreateCustomerRequest;
import com.box.library.request.UpdateCustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(CreateCustomerRequest request) {
        return repository.save(toEntity(request));
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
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
        return new Customer(request.name(), request.cpf());
    }
}
