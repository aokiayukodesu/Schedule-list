package com.example.schedule.demo.Exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = IdCheckValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCheckValid {
    String message() default "入力されたidは存在しません";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

