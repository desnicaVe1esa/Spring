package ru.ma1gus.Spring_Boot_Security_Practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ma1gus.Spring_Boot_Security_Practice.models.Person;
import ru.ma1gus.Spring_Boot_Security_Practice.repositories.PeopleRepository;
import ru.ma1gus.Spring_Boot_Security_Practice.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Это не тот пидарас");
        }

        return new PersonDetails(person.get());
    }
}
