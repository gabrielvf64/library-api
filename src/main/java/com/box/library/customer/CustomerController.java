package com.box.library.customer;

import com.box.library.jwt.JwtUserDetails;
import com.box.library.request.CreateCustomerRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento dos clientes")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Customer> create(@Valid @RequestBody CreateCustomerRequest request,
                                           @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        var entity = service.save(request, jwtUserDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Customer>> findAll() {
        var list = service.findAll();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/details")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Customer> getDetails(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        var entity = service.findUserById(jwtUserDetails.getId());
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Customer> update(@PathVariable Long id,
                                           @RequestBody CreateCustomerRequest request) {
        var updatedEntity = service.update(id, request);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
