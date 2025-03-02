package com.box.library.customer;

import com.box.library.jwt.JwtUserDetails;
import com.box.library.request.CreateCustomerRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
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
}
