package com.mehmetsolak.fileservice.validators.impl;

import com.mehmetsolak.fileservice.validators.MaxFileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {

    private long maxFileSize;

    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        this.maxFileSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        return file.getSize() <= maxFileSize;
    }
}
