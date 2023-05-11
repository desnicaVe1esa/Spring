package ru.ma1gus.practice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ma1gus.practice.dao.PersonDao;
import ru.ma1gus.practice.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // Посмотреть, есть ли человек с таким же email'ом в БД
        if (personDao.show(person.getEmail()) != null) {
            errors.rejectValue("email","", "Этот почтовый адрес уже используется");
        }
    }
}
