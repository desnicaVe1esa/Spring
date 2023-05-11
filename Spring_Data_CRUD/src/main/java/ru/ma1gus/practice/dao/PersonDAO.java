package ru.ma1gus.practice.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ma1gus.practice.models.Person;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public void testNPlus1() {
        Session session = entityManager.unwrap(Session.class);

//        /////////////////////////////
//        //// Проблема N+1 ///////////
//        /////////////////////////////
//        // 1 запрос
//        List<Person> people = session.createQuery("select p from Person p", Person.class)
//                .getResultList();
//
//        // N запросов
//        for (Person person : people) {
//            System.out.println("person " + person.getName() + " has: " + person.getItem());
//        }

        /////////////////////////////
        //// Решение проблемы N+1 ///
        /////////////////////////////
        // SQL: A left join B -> Результирующая таблица
        Set<Person> people = new HashSet<Person>(session.createQuery("select p from Person p left join fetch p.items")
                .getResultList());
        for (Person person : people) {
            System.out.println("person " + person.getName() + " has: " + person.getItem());
        }
    }
}
