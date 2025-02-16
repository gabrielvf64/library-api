package com.box.library.exception;

public class NoFilterProvidedException extends RuntimeException {
    public NoFilterProvidedException() {
        super("At least one search parameter must be provided.");
    }
}