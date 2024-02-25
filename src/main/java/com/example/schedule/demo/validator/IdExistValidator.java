package com.example.schedule.demo.validator;

import com.example.schedule.demo.Exception.UserNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.service.ScheduleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;


public class IdExistValidator implements ConstraintValidator<IdExistCheck, Optional<Integer>> {

    ScheduleService scheduleService;

    @Override
    public void initialize(IdExistCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(Optional<Integer> value, ConstraintValidatorContext context) {
        if (value.isPresent()) {
            return true;
        }
        try {
            scheduleService.findById(value.get());
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}

