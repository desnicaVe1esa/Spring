//package ru.ma1gus.Spring_Boot_Security_Practice.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import ru.ma1gus.Spring_Boot_Security_Practice.services.PersonDetailService;
//
//import java.util.Collections;
//
////////////////////////////////////////////////////////////////////////////////
// Для кастомной логики аутентификации, которая используется в крупных фирмах //
////////////////////////////////////////////////////////////////////////////////
//@Component
//public class AuthProviderImpl implements AuthenticationProvider {
//
//    private final PersonDetailService personDetailService;
//
//    @Autowired
//    public AuthProviderImpl(PersonDetailService personDetailService) {
//        this.personDetailService = personDetailService;
//    }
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//
//        UserDetails personDetails = personDetailService.loadUserByUsername(username);
//
//        String password = authentication.getCredentials().toString();
//
//        if (!password.equals(personDetails.getPassword())){
//            throw new BadCredentialsException("Не тот пароль, чисти говно блядь");
//        }
//        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
