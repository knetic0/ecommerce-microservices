package com.mehmetsolak.fileservice.validators;

import com.mehmetsolak.fileservice.validators.impl.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Documented
public @interface MaxFileSize {
    String message() default "";

    long value(); // bytes

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
