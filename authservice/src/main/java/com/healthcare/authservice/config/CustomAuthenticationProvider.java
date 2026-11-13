package com.healthcare.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider
        implements AuthenticationProvider {

   // @Autowired
   // private UserDetailsService userDetailsService;

   // @Autowired
   // private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
            Authentication authentication) {

        String username = authentication.getName();

        String password =
                authentication.getCredentials().toString();

       // UserDetails user =
               // userDetailsService.loadUserByUsername(username);

		/*
		 * if (!passwordEncoder.matches( password, user.getPassword())) {
		 * 
		 * throw new BadCredentialsException( "Invalid username or password"); }
		 */

        return new UsernamePasswordAuthenticationToken(
        		username,
                null,null
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
