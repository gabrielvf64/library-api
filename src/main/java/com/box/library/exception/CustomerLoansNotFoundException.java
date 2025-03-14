package com.box.library.exception;

public class CustomerLoansNotFoundException extends RuntimeException {
    public CustomerLoansNotFoundException(Long customerId) {
        super("Loans not found for customer with id: " + customerId);
    }
}
