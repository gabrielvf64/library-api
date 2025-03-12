package com.box.library.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomErrorMessage {

    private String path;
    private String method;
    private int statusCode;
    private String statusMessage;
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> errors;

    public CustomErrorMessage(HttpServletRequest request,
                              HttpStatus status,
                              String message) {

        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.errorMessage = message;
    }

    public CustomErrorMessage(HttpServletRequest request,
                              HttpStatus status,
                              String message,
                              BindingResult bindingResult) {

        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.errorMessage = message;
        addErrors(bindingResult);
    }

    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
