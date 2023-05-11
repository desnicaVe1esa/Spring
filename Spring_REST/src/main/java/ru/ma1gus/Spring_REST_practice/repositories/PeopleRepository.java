package ru.ma1gus.Spring_REST_practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ma1gus.Spring_REST_practice.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
