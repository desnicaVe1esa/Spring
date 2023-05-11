package ru.ma1gus.JWT_token_practice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ma1gus.JWT_token_practice.models.Person;
import ru.ma1gus.JWT_token_practice.services.PersonDetailService;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailService personDetailService;

    @Autowired
    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;
        try {
        personDetailService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignore) {
            return;
        }
        errors.rejectValue("username", "69", "Такой пидарас уже есть");
    }
}
