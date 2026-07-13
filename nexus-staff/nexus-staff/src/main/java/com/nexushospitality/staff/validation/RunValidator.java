package com.nexushospitality.staff.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RunValidator implements ConstraintValidator<RunValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        String run = value.replace(".", "").replace("-", "").toUpperCase();
        if (!run.matches("[0-9]{7,8}[0-9K]")) {
            return false;
        }
        int sum = 0;
        int multiplier = 2;
        for (int i = run.length() - 2; i >= 0; i--) {
            sum += Character.digit(run.charAt(i), 10) * multiplier;
            multiplier = multiplier == 7 ? 2 : multiplier + 1;
        }
        int result = 11 - (sum % 11);
        char expected = result == 11 ? '0' : result == 10 ? 'K' : Character.forDigit(result, 10);
        return run.charAt(run.length() - 1) == expected;
    }
}
