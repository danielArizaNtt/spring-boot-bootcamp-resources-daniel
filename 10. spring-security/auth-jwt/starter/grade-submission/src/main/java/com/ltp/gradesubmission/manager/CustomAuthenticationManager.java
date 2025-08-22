package com.ltp.gradesubmission.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    // Get user name and see if it exists in the database
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String passedUsername = authentication.getName();
        String passedPassword = authentication.getCredentials().toString();

        User user = userService.getUser(passedUsername);
        String actualPassword = user.getPassword();

        Boolean isWrongPassword = !bCryptPasswordEncoder.matches(passedPassword, actualPassword);

        if (isWrongPassword) {
            throw new BadCredentialsException("You provided an incorrect passoword.");
        }

        return new UsernamePasswordAuthenticationToken(passedUsername, actualPassword);
    }

}
