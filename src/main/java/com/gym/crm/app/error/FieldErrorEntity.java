package com.gym.crm.app.error;

import org.springframework.validation.FieldError;

public record FieldErrorEntity
        (
                String field,
                String message
        ) {
    public FieldErrorEntity(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}