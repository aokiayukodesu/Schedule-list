package com.example.schedule.demo.Exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.NoSuchElementException;
import java.util.Optional;

public class IdCheckValidator implements ConstraintValidator<IdCheckValid, Optional<Integer>> {

    @Override
    public void initialize(IdCheckValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Optional<Integer> value, ConstraintValidatorContext context) {
        if (value.isPresent()) {
            return true;
        }

        try {
            return
        } catch (UserNotFoundExcption e) {
            return
        }
    }
}