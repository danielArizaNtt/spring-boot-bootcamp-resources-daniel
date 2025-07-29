package com.ltp.javagram;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastnameMatchesValidator implements ConstraintValidator<LastnameMatches, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if (firstName.equals(lastName)) return true;
        
        return false;
    }
    
}
