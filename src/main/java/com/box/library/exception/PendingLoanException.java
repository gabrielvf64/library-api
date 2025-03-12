package com.box.library.exception;

public class PendingLoanException extends RuntimeException {
    public PendingLoanException(Long customerId) {
        super("Customer with id " + customerId + " has pending loan.");
    }
}
